import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBankingTransaction, BankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';
import { BankingTransactionService } from './banking-transaction.service';
import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { BankingAccountService } from 'app/entities/bankMS/banking-account';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity';

@Component({
  selector: 'jhi-banking-transaction-update',
  templateUrl: './banking-transaction-update.component.html'
})
export class BankingTransactionUpdateComponent implements OnInit {
  isSaving: boolean;

  bankingaccounts: IBankingAccount[];

  bankingentities: IBankingEntity[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required]],
    date: [null, [Validators.required]],
    ammount: [null, [Validators.required]],
    transactionType: [null, [Validators.required]],
    extOriginAccount: [],
    extOriginAccountType: [],
    extOriginAccountBank: [],
    extDestinationAccount: [],
    extDestinationAccountType: [],
    extDestinationAccountBank: [],
    originAccountId: [],
    destinationAccountId: [],
    bankingEntityId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bankingTransactionService: BankingTransactionService,
    protected bankingAccountService: BankingAccountService,
    protected bankingEntityService: BankingEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bankingTransaction }) => {
      this.updateForm(bankingTransaction);
    });
    this.bankingAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBankingAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBankingAccount[]>) => response.body)
      )
      .subscribe((res: IBankingAccount[]) => (this.bankingaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bankingEntityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBankingEntity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBankingEntity[]>) => response.body)
      )
      .subscribe((res: IBankingEntity[]) => (this.bankingentities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bankingTransaction: IBankingTransaction) {
    this.editForm.patchValue({
      id: bankingTransaction.id,
      number: bankingTransaction.number,
      date: bankingTransaction.date != null ? bankingTransaction.date.format(DATE_TIME_FORMAT) : null,
      ammount: bankingTransaction.ammount,
      transactionType: bankingTransaction.transactionType,
      extOriginAccount: bankingTransaction.extOriginAccount,
      extOriginAccountType: bankingTransaction.extOriginAccountType,
      extOriginAccountBank: bankingTransaction.extOriginAccountBank,
      extDestinationAccount: bankingTransaction.extDestinationAccount,
      extDestinationAccountType: bankingTransaction.extDestinationAccountType,
      extDestinationAccountBank: bankingTransaction.extDestinationAccountBank,
      originAccountId: bankingTransaction.originAccountId,
      destinationAccountId: bankingTransaction.destinationAccountId,
      bankingEntityId: bankingTransaction.bankingEntityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bankingTransaction = this.createFromForm();
    if (bankingTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.bankingTransactionService.update(bankingTransaction));
    } else {
      this.subscribeToSaveResponse(this.bankingTransactionService.create(bankingTransaction));
    }
  }

  private createFromForm(): IBankingTransaction {
    return {
      ...new BankingTransaction(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      ammount: this.editForm.get(['ammount']).value,
      transactionType: this.editForm.get(['transactionType']).value,
      extOriginAccount: this.editForm.get(['extOriginAccount']).value,
      extOriginAccountType: this.editForm.get(['extOriginAccountType']).value,
      extOriginAccountBank: this.editForm.get(['extOriginAccountBank']).value,
      extDestinationAccount: this.editForm.get(['extDestinationAccount']).value,
      extDestinationAccountType: this.editForm.get(['extDestinationAccountType']).value,
      extDestinationAccountBank: this.editForm.get(['extDestinationAccountBank']).value,
      originAccountId: this.editForm.get(['originAccountId']).value,
      destinationAccountId: this.editForm.get(['destinationAccountId']).value,
      bankingEntityId: this.editForm.get(['bankingEntityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankingTransaction>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackBankingAccountById(index: number, item: IBankingAccount) {
    return item.id;
  }

  trackBankingEntityById(index: number, item: IBankingEntity) {
    return item.id;
  }
}
