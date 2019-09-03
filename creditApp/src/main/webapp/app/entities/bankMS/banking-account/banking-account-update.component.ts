import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBankingAccount, BankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { BankingAccountService } from './banking-account.service';
import { CustomerService } from "app/entities/customer";
import { ICustomer, Customer } from "app/shared/model/customer.model";
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from "ng-jhipster";
import { IBankingEntity } from "app/shared/model/bankMS/banking-entity.model";
import { BankingEntityService } from "app/entities/bankMS/banking-entity";

@Component({
  selector: 'jhi-banking-account-update',
  templateUrl: './banking-account-update.component.html'
})
export class BankingAccountUpdateComponent implements OnInit {
  isSaving: boolean;
  customers: ICustomer[];
  bankingEntities: IBankingEntity[];

  editForm = this.fb.group({
    id: [],
    number: [null, [Validators.required]],
    accountType: [null, [Validators.required]],
    currentBalance: [null, [Validators.required]],
    availableBalance: [null, [Validators.required]],
    customerIdentification: [null, [Validators.required]],
    customerIdentificationType: [null, [Validators.required]],
    customerCountry: [null, [Validators.required]],
    bankingEntityMnemonic: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('[A-Z0-9]+')]],
    customerId: [null, [Validators.required]]
  });

  constructor(protected bankingAccountService: BankingAccountService, 
          protected activatedRoute: ActivatedRoute, 
          private fb: FormBuilder, 
          protected customerService: CustomerService,
          protected jhiAlertService: JhiAlertService,
          protected bankingEntityService: BankingEntityService) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bankingAccount }) => {
      this.updateForm(bankingAccount);
    });
    this.customerService
    .query()
    .pipe(
      filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
      map((response: HttpResponse<ICustomer[]>) => response.body)
    )
    .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bankingEntityService
    .query()
    .pipe(
      filter((mayBeOk: HttpResponse<IBankingEntity[]>) => mayBeOk.ok),
      map((response: HttpResponse<IBankingEntity[]>) => response.body)
    )
    .subscribe((res: IBankingEntity[]) => (this.bankingEntities = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    //For new accounts
    if(bankingAccount.id === undefined || bankingAccount.id === null || bankingAccount.id === 0)
      this.patchAccountBalance();
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
  
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
  
  onCustomerChange(target: any){
    let customerFiltered = this.customers.filter((customer: ICustomer) => {
      return customer.id === parseInt(target.value);
    });
    let selectedCustomer = customerFiltered.length > 0 ? customerFiltered[0] : new Customer();
    this.editForm.patchValue({ 
      customerIdentification: selectedCustomer.identificationNumber,
      customerIdentificationType: selectedCustomer.identificationType,
      customerCountry: selectedCustomer.country
    });
  }
  
  patchAccountBalance(){
    this.editForm.patchValue({ 
      currentBalance: 0,
      availableBalance: 0
    });
  }
  
  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
  
  trackBankingEntityById(index: number, item: IBankingEntity) {
    return item.mnemonic;
  }
}
