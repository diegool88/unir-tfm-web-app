import { Component, OnInit, OnDestroy } from '@angular/core';
import { ILoanProcess, LoanProcessStatus } from "app/shared/model/loanMS/loan-process.model";
import { Subscription } from "rxjs";
import { LoanProcessService } from "app/entities/loanMS/loan-process";
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { AccountService } from "app/core";
import { ILoanProcessWrapper } from 'app/shared/model/loanMS/loan-process-wrapper.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAddress } from "app/shared/model/address.model";
import { ITelephoneNumber } from "app/shared/model/telephone-number.model";
import { IPersonalReference } from "app/shared/model/personal-reference.model";
import { IBankingAccount } from "app/shared/model/bankMS/banking-account.model";
import { IBankingTransaction, BankingTransaction, TransactionType, AccountType } from "app/shared/model/bankMS/banking-transaction.model";
import { AddressService } from "app/entities/address";
import { TelephoneNumberService } from "app/entities/telephone-number";
import { PersonalReferenceService } from "app/entities/personal-reference";
import { CustomerService } from "app/entities/customer";
import { BankingTransactionService } from 'app/entities/bankMS/banking-transaction';
import { BankingAccountService } from 'app/entities/bankMS/banking-account';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity';
import { IBankingEntity } from "app/shared/model/bankMS/banking-entity.model";
import * as moment from 'moment';

@Component({
  selector: 'jhi-loan-process-official',
  templateUrl: './loan-process-official.component.html',
  styleUrls: ['./loan-process-official.component.scss']
})
export class LoanProcessOfficialComponent implements OnInit, OnDestroy {
    loanProcesses: ILoanProcess[];
    currentAccount: any;
    eventSubscriber: Subscription;
    selectedLoanProcess: ILoanProcessWrapper;
    customer?: ICustomer;
    addresses?: IAddress[];
    telephoneNumbers?: ITelephoneNumber[];
    personalReferences?: IPersonalReference[];
    bankingAccount?: IBankingAccount;
    
    constructor(
      protected loanProcessService: LoanProcessService,
      protected jhiAlertService: JhiAlertService,
      protected eventManager: JhiEventManager,
      protected accountService: AccountService,
      protected customerService: CustomerService,
      protected addressService: AddressService,
      protected telephoneNumberService: TelephoneNumberService,
      protected personalReferenceService: PersonalReferenceService,
      protected bankingTransactionService: BankingTransactionService,
      protected bankingAccountService: BankingAccountService,
      protected bankingEntityService: BankingEntityService,
      protected dataUtils: JhiDataUtils
    ) {}
    
    loadAll() {
      this.loanProcessService
        .query()
        .pipe(
          filter((res: HttpResponse<ILoanProcess[]>) => res.ok),
          map((res: HttpResponse<ILoanProcess[]>) => res.body)
        )
        .subscribe(
          (res: ILoanProcess[]) => {
            this.loanProcesses = res;
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    
    ngOnInit() {
      this.loadAll();
      this.accountService.identity().then(account => {
        this.currentAccount = account;
      });
      this.registerChangeInLoanProcesses();
    }
    
    ngOnDestroy() {
      this.eventManager.destroy(this.eventSubscriber);
    }
    
    trackId(index: number, item: ILoanProcess) {
      return item.id;
    }
    
    registerChangeInLoanProcesses() {
      this.eventSubscriber = this.eventManager.subscribe('loanProcessListModification', response => this.loadAll());
    }
    
    showLoanDetail(loanProcess: ILoanProcess){
        this.loanProcessService
        .findComplete(loanProcess.id)
        .pipe(
          filter((res: HttpResponse<ILoanProcessWrapper>) => res.ok),
          map((res: HttpResponse<ILoanProcessWrapper>) => res.body)
        )
        .subscribe(
         (res: ILoanProcessWrapper) => {
          this.selectedLoanProcess = res;
          if(this.selectedLoanProcess){
              this.customerService.findByIdentification(this.selectedLoanProcess.loanProcess.debtorIdentificationType, this.selectedLoanProcess.loanProcess.debtorIdentification, this.selectedLoanProcess.loanProcess.debtorCountry)
              .pipe(
                filter((res: HttpResponse<ICustomer>) => res.ok),
                map((res: HttpResponse<ICustomer>) => res.body)
              ).subscribe((res: ICustomer) => {
                  this.customer = res;
                  this.addressService
                  .queryByCustomer(this.customer.id)
                  .pipe(
                    filter((res: HttpResponse<IAddress[]>) => res.ok),
                    map((res: HttpResponse<IAddress[]>) => res.body)
                  )
                  .subscribe(
                   (res: IAddress[]) => {
                       this.addresses = res;
                   });
                  this.telephoneNumberService
                  .queryByCustomer(this.customer.id)
                  .pipe(
                    filter((res: HttpResponse<ITelephoneNumber[]>) => res.ok),
                    map((res: HttpResponse<ITelephoneNumber[]>) => res.body)
                  )
                  .subscribe(
                   (res: ITelephoneNumber[]) => {
                       this.telephoneNumbers = res;
                   });
                  this.personalReferenceService
                  .queryByCustomer(this.customer.id)
                  .pipe(
                    filter((res: HttpResponse<IPersonalReference[]>) => res.ok),
                    map((res: HttpResponse<IPersonalReference[]>) => res.body)
                  )
                  .subscribe(
                   (res: IPersonalReference[]) => {
                       this.personalReferences = res;
                   });
              });
          }
         },
         (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    
    getAmortizationScheduleAmountTotal() {
      return this.selectedLoanProcess.amortizationSchedule
        .map(item => {
          return item.amount;
        })
        .reduce((sum, curr) => sum + curr);
    }

    getAmortizationScheduleInterestTotal() {
      return this.selectedLoanProcess.amortizationSchedule
        .map(item => {
          return item.interest;
        })
        .reduce((sum, curr) => sum + curr);
    }
    
    byteSize(field) {
      return this.dataUtils.byteSize(field);
    }
    
    openFile(contentType, field) {
      return this.dataUtils.openFile(contentType, field);
    }
    
    onCancel(){}
    
    onApprove(){
        this.bankingAccountService.findByNumber(this.selectedLoanProcess.loanProcess.bankingAccountNumber, this.selectedLoanProcess.loanProcess.bankingAccountType, this.selectedLoanProcess.loanProcess.bankingEntityMnemonic)
        .pipe(
          filter((res: HttpResponse<IBankingAccount>) => res.ok),
          map((res: HttpResponse<IBankingAccount>) => res.body)
        )
        .subscribe((res: IBankingAccount) => {
          this.bankingAccount = res;
          this.bankingEntityService.findByMnemonic(this.bankingAccount.bankingEntityMnemonic)
          .pipe(
            filter((res: HttpResponse<IBankingEntity>) => res.ok),
            map((res: HttpResponse<IBankingEntity>) => res.body)
          )
          .subscribe((res: IBankingEntity) => {
             const bankingTransaction: IBankingTransaction = new BankingTransaction();
             bankingTransaction.ammount = this.selectedLoanProcess.loanProcess.requestedAmount;
             bankingTransaction.date = moment();
             bankingTransaction.number = this.selectedLoanProcess.loanProcess.id;
             bankingTransaction.transactionType = TransactionType.DEPOSIT;
             bankingTransaction.destinationAccountId = this.bankingAccount.id;
             bankingTransaction.bankingEntityId = res.id;
             this.bankingTransactionService.createWithTransfer(bankingTransaction)
             .pipe(
               filter((res: HttpResponse<IBankingTransaction>) => res.ok),
               map((res: HttpResponse<IBankingTransaction>) => res.body)
             )
             .subscribe((res: IBankingTransaction) => {
                 if(res){
                     this.selectedLoanProcess.loanProcess.loanProcessStatus = LoanProcessStatus.APPROVED;
                     this.loanProcessService.update(this.selectedLoanProcess.loanProcess)
                     .pipe(
                       filter((res: HttpResponse<ILoanProcess>) => res.ok),
                       map((res: HttpResponse<ILoanProcess>) => res.body)
                     )
                     .subscribe((res: ILoanProcess) => {
                       this.loadAll();
                       this.selectedLoanProcess = null;
                       this.jhiAlertService.success('Operacion realizada con exito', null, null);
                     });
                 }
             });
          });
          
        });
    }
    
    onDeny(){}
    
    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
}
