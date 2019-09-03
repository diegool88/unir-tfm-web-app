import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILoanProcess, LoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from './loan-process.service';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { WarrantyService } from 'app/entities/loanMS/warranty';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity/banking-entity.service';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { ProductService } from 'app/entities/bankMS/product/product.service';
import { IProduct, Product } from 'app/shared/model/bankMS/product.model';
import { WizardFooterService } from 'app/layouts/wizard/wizard-footer.service';
import { WizardService } from 'app/layouts/wizard/wizard.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { AmortizationTableService } from 'app/entities/loanMS/amortization-table';
import { Moment } from 'moment';
import { IBankingAccount, BankingAccount } from "app/shared/model/bankMS/banking-account.model";
import { BankingAccountService } from "app/entities/bankMS/banking-account";

@Component({
  selector: 'jhi-loan-process-update',
  templateUrl: './loan-process-update.component.html'
})
export class LoanProcessUpdateComponent implements OnInit {
  isSaving: boolean;

  warranties: IWarranty[];
  startDateDp: any;
  endDateDp: any;
  bankingEntities: IBankingEntity[];
  products: IProduct[];
  selectedProduct?: IProduct;
  mode: any;
  customer?: ICustomer;
  amortizationSchedule?: IAmortizationTable[];
  customerAccounts?: IBankingAccount[];
  selectedAccount?: IBankingAccount;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z0-9\\s]+')]],
    requestedAmount: [null, [Validators.required]],
    givenAmount: [],
    loanPeriod: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    justification: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern('[A-Za-z\\s]+')]],
    debtorIdentification: [null, [Validators.required]],
    debtorIdentificationType: [null, [Validators.required]],
    debtorCountry: [null, [Validators.required]],
    bankingEntityMnemonic: [null, [Validators.required]],
    bankingProductMnemonic: [null, [Validators.required]],
    rulesEngineResult: [],
	bankingAccountNumber: [null, [Validators.required]],
    bankingAccountType: [],
    bankingAccountEntityMnemonic: [],
    loanProcessStatus: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loanProcessService: LoanProcessService,
    protected warrantyService: WarrantyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected bankingEntityService: BankingEntityService,
    protected productService: ProductService,
    protected wizardFooterService: WizardFooterService,
    protected wizardService: WizardService,
    protected amortizationTableService: AmortizationTableService,
    protected accountService: BankingAccountService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loanProcess }) => {
      this.updateForm(loanProcess);
    });
    this.activatedRoute.queryParams.subscribe(queryParams => {
      if (queryParams && queryParams.mode) {
        this.mode = queryParams.mode;
        if (this.mode === 'wizard') {
          this.wizardFooterService.setFormValid(false);
          this.customer = this.wizardService.getCustomer();
          this.selectedAccount = this.wizardService.getSelectedAccount();
          this.updateForm(this.wizardService.getLoanProcess());
          this.updateFormBankingAccount(this.selectedAccount);
          this.initializeCustomerInformation();
          this.amortizationSchedule = this.wizardService.getAmortizationSchedule();
          this.accountService
          .queryByCustomer()
          .pipe(
            filter((mayBeOk: HttpResponse<IBankingAccount[]>) => mayBeOk.ok),
            map((response: HttpResponse<IBankingAccount[]>) => response.body)
          )
          .subscribe((res: IBankingAccount[]) => (this.customerAccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
      }
    });
    this.warrantyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWarranty[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWarranty[]>) => response.body)
      )
      .subscribe((res: IWarranty[]) => (this.warranties = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bankingEntityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBankingEntity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBankingEntity[]>) => response.body)
      )
      .subscribe((res: IBankingEntity[]) => (this.bankingEntities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(loanProcess: ILoanProcess) {
    if (loanProcess.bankingEntityMnemonic !== undefined && loanProcess.bankingEntityMnemonic !== null) {
      this.queryProductByBankEntity({ value: loanProcess.bankingEntityMnemonic });
    }
    this.editForm.patchValue({
      id: loanProcess.id,
      name: loanProcess.name,
      requestedAmount: loanProcess.requestedAmount,
      givenAmount: loanProcess.givenAmount,
      loanPeriod: loanProcess.loanPeriod,
      startDate: loanProcess.startDate,
      endDate: loanProcess.endDate,
      justification: loanProcess.justification,
      debtorIdentification: loanProcess.debtorIdentification,
      debtorIdentificationType: loanProcess.debtorIdentificationType,
      debtorCountry: loanProcess.debtorCountry,
      bankingEntityMnemonic: loanProcess.bankingEntityMnemonic,
      bankingProductMnemonic: loanProcess.bankingProductMnemonic,
      rulesEngineResult: loanProcess.rulesEngineResult,
      bankingAccountNumber: loanProcess.bankingAccountNumber,
      bankingAccountType: loanProcess.bankingAccountType,
      bankingAccountEntityMnemonic: loanProcess.bankingAccountEntityMnemonic,
      loanProcessStatus: loanProcess.loanProcessStatus
    });
  }
  
  updateFormBankingAccount(selectedBankingAccount: IBankingAccount) {
      if(selectedBankingAccount){
          this.editForm.patchValue({ 
            bankingAccountNumber: selectedBankingAccount.number,
            bankingAccountType: selectedBankingAccount.accountType,
            bankingAccountEntityMnemonic: selectedBankingAccount.bankingEntityMnemonic
          });
      }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const loanProcess = this.createFromForm();
    
    if (this.mode === 'wizard') {
      this.loanProcessService.processBusinessRules(this.customer.birthDate.format('YYYY-MM-DD'), this.customer.monthlyIncome, this.customer.genre.toString(), loanProcess.requestedAmount, loanProcess.loanPeriod)
      .pipe(
        filter((mayBeOk: HttpResponse<boolean>) => mayBeOk.ok),
        map((response: HttpResponse<boolean>) => response.body)
      )
      .subscribe((res: boolean) => { 
          loanProcess.rulesEngineResult = res;
          this.wizardService.setLoanProcess(loanProcess);
          this.wizardService.setAmortizationSchedule(this.amortizationSchedule);
          this.wizardService.setSelectedAccount(this.selectedAccount);
          this.wizardFooterService.setFormValid(true);
      }, 
      (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    if (loanProcess.id !== undefined) {
      this.subscribeToSaveResponse(this.loanProcessService.update(loanProcess));
    } else {
      this.subscribeToSaveResponse(this.loanProcessService.create(loanProcess));
    }
  }

  private createFromForm(): ILoanProcess {
    return {
      ...new LoanProcess(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      requestedAmount: this.editForm.get(['requestedAmount']).value,
      givenAmount: this.editForm.get(['givenAmount']).value,
      loanPeriod: this.editForm.get(['loanPeriod']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      justification: this.editForm.get(['justification']).value,
      debtorIdentification: this.editForm.get(['debtorIdentification']).value,
      debtorIdentificationType: this.editForm.get(['debtorIdentificationType']).value,
      debtorCountry: this.editForm.get(['debtorCountry']).value,
      bankingEntityMnemonic: this.editForm.get(['bankingEntityMnemonic']).value,
      bankingProductMnemonic: this.editForm.get(['bankingProductMnemonic']).value,
      rulesEngineResult: this.editForm.get(['rulesEngineResult']).value,
      bankingAccountNumber: this.editForm.get(['bankingAccountNumber']).value,
      bankingAccountType: this.editForm.get(['bankingAccountType']).value,
      bankingAccountEntityMnemonic: this.editForm.get(['bankingAccountEntityMnemonic']).value,
      loanProcessStatus: this.editForm.get(['loanProcessStatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanProcess>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    if (this.mode !== 'wizard') {
      this.previousState();
    } else {
      this.wizardFooterService.setFormValid(true);
      this.jhiAlertService.success("Operacion realizada con exito", null, null);
    }
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackWarrantyById(index: number, item: IWarranty) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  queryProductByBankEntity(target: any) {
    this.editForm.patchValue({ bankingProductMnemonic: null });
    this.productService
      .queryByBankingEntityMnemonic(target.value)
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  protected initializeCustomerInformation() {
    this.editForm.patchValue({
      name: 'New Loan P C ' + this.customer.identificationNumber,
      debtorIdentification: this.customer.identificationNumber,
      debtorIdentificationType: this.customer.identificationType,
      debtorCountry: this.customer.country
    });
  }

  changeEndDate(event: any) {
    if (
      this.editForm.get(['loanPeriod']).value !== undefined &&
      this.editForm.get(['loanPeriod']).value !== null &&
      typeof event === 'object' &&
      event instanceof moment
    ) {
      const loanPeriodYears = parseInt(this.editForm.get(['loanPeriod']).value);
      const startDate = (event as Moment).toDate();
      const endDate = new Date(startDate.getFullYear() + loanPeriodYears, startDate.getMonth(), startDate.getDate());
      this.editForm.patchValue({ endDate: moment(endDate) });
      console.log(endDate);
    } else if (
      this.editForm.get(['startDate']).value !== undefined &&
      this.editForm.get(['startDate']).value !== null &&
      typeof event === 'number'
    ) {
      const loanPeriodYears = event;
      const startDate = this.editForm.get(['startDate']).value.toDate();
      const endDate = new Date(startDate.getFullYear() + loanPeriodYears, startDate.getMonth(), startDate.getDate());
      this.editForm.patchValue({ endDate: moment(endDate) });
    }
  }

  onProductChange(target: any) {
    const productsFiltered = this.products.filter((product: IProduct) => {
      return product.mnemonic === target.value;
    });
    this.selectedProduct = productsFiltered.length > 0 ? productsFiltered[0] : new Product();
    this.editForm.patchValue({ bankingProductMnemonic: this.selectedProduct.mnemonic });
  }
  
  onBankingAccountChange(target: any) {
      const accountsFiltered = this.customerAccounts.filter((account: IBankingAccount) => {
        return account.number === parseInt(target.value);
      });
      this.selectedAccount = accountsFiltered.length > 0 ? accountsFiltered[0] : new BankingAccount();
      this.editForm.patchValue({ 
        bankingAccountNumber: this.selectedAccount.number,
        bankingAccountType: this.selectedAccount.accountType,
        bankingAccountEntityMnemonic: this.selectedAccount.bankingEntityMnemonic
      });      
  }

  calculateAmortizationSchedule(event: any) {
    this.amortizationTableService
      .calculate(
        this.editForm.get(['requestedAmount']).value,
        this.selectedProduct.interestRate,
        this.editForm.get(['startDate']).value.format('YYYY-MM-DD'),
        this.editForm.get(['loanPeriod']).value
      )
      .pipe(
        filter((mayBeOk: HttpResponse<IAmortizationTable[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAmortizationTable[]>) => response.body)
      )
      .subscribe((res: IAmortizationTable[]) => (this.amortizationSchedule = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  getAmortizationScheduleAmountTotal() {
    return this.amortizationSchedule
      .map(item => {
        return item.amount;
      })
      .reduce((sum, curr) => sum + curr);
  }

  getAmortizationScheduleInterestTotal() {
    return this.amortizationSchedule
      .map(item => {
        return item.interest;
      })
      .reduce((sum, curr) => sum + curr);
  }

  trackProductById(index: number, item: IProduct) {
    return item.mnemonic;
  }

  trackBankingEntityById(index: number, item: IBankingEntity) {
    return item.mnemonic;
  }
  
  trackBankingAccountNumber(index: number, item: IBankingAccount) {
    return item.number;
  }
}
