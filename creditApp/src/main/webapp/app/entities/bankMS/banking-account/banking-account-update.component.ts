import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBankingAccount, BankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { BankingAccountService } from './banking-account.service';

@Component({
  selector: 'jhi-banking-account-update',
  templateUrl: './banking-account-update.component.html'
})
export class BankingAccountUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required]],
    accountType: [null, [Validators.required]],
    currentBalance: [null, [Validators.required]],
    availableBalance: [null, [Validators.required]],
    customerIdentification: [null, [Validators.required]],
    customerIdentificationType: [null, [Validators.required]],
    customerCountry: [null, [Validators.required]],
    bankingEntityMnemonic: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('[A-Z0-9]+')]]
  });

  constructor(protected bankingAccountService: BankingAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bankingAccount }) => {
      this.updateForm(bankingAccount);
    });
  }

  updateForm(bankingAccount: IBankingAccount) {
    this.editForm.patchValue({
      id: bankingAccount.id,
      number: bankingAccount.number,
      accountType: bankingAccount.accountType,
      currentBalance: bankingAccount.currentBalance,
      availableBalance: bankingAccount.availableBalance,
      customerIdentification: bankingAccount.customerIdentification,
      customerIdentificationType: bankingAccount.customerIdentificationType,
      customerCountry: bankingAccount.customerCountry,
      bankingEntityMnemonic: bankingAccount.bankingEntityMnemonic
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bankingAccount = this.createFromForm();
    if (bankingAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.bankingAccountService.update(bankingAccount));
    } else {
      this.subscribeToSaveResponse(this.bankingAccountService.create(bankingAccount));
    }
  }

  private createFromForm(): IBankingAccount {
    return {
      ...new BankingAccount(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      accountType: this.editForm.get(['accountType']).value,
      currentBalance: this.editForm.get(['currentBalance']).value,
      availableBalance: this.editForm.get(['availableBalance']).value,
      customerIdentification: this.editForm.get(['customerIdentification']).value,
      customerIdentificationType: this.editForm.get(['customerIdentificationType']).value,
      customerCountry: this.editForm.get(['customerCountry']).value,
      bankingEntityMnemonic: this.editForm.get(['bankingEntityMnemonic']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankingAccount>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
