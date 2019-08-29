import { Component, OnInit } from '@angular/core';
import { ICustomer } from 'app/shared/model/customer.model';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { WizardService } from '../wizard.service';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { IAddress } from "app/shared/model/address.model";
import { AddressService } from "app/entities/address";
import { HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITelephoneNumber } from "app/shared/model/telephone-number.model";
import { TelephoneNumberService } from "app/entities/telephone-number";
import { IPersonalReference } from "app/shared/model/personal-reference.model";
import { PersonalReferenceService } from "app/entities/personal-reference";
import { Observable } from "rxjs";
import { filter, map } from 'rxjs/operators';
import { LoanProcessService } from "app/entities/loanMS/loan-process";
import { WarrantyService } from "app/entities/loanMS/warranty";
import { AmortizationTableService } from "app/entities/loanMS/amortization-table";
import { Router } from "@angular/router";

@Component({
  selector: 'jhi-wizard-summary',
  templateUrl: './wizard-summary.component.html',
  styleUrls: ['./wizard-summary.component.scss']
})
export class WizardSummaryComponent implements OnInit {
  isSaving: boolean;
  customer?: ICustomer;
  amortizationSchedule?: IAmortizationTable[];
  selectedAccount?: IBankingAccount;
  loanProcess?: ILoanProcess;
  warranties?: IWarranty[];
  addresses?: IAddress[];
  telephoneNumbers?: ITelephoneNumber[];
  personalReferences?: IPersonalReference[];

  constructor(protected wizardService: WizardService,
          protected addressService: AddressService,
          protected telephoneNumberService: TelephoneNumberService,
          protected personalReferenceService: PersonalReferenceService,
          protected loanProcessService: LoanProcessService,
          protected warratyService: WarrantyService,
          protected amortizationTableService: AmortizationTableService,
          protected dataUtils: JhiDataUtils,
          protected jhiAlertService: JhiAlertService,
          private router: Router) {}

  ngOnInit() {
    this.customer = this.wizardService.getCustomer();
    this.amortizationSchedule = this.wizardService.getAmortizationSchedule();
    this.selectedAccount = this.wizardService.getSelectedAccount();
    this.loanProcess = this.wizardService.getLoanProcess();
    this.warranties = this.wizardService.getWarranties();
    this.addressService
    .query()
    .subscribe(
      (res: HttpResponse<IAddress[]>) => { 
          this.addresses = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.telephoneNumberService
    .query()
    .subscribe(
      (res: HttpResponse<ITelephoneNumber[]>) => {
          this.telephoneNumbers = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.personalReferenceService
    .query()
    .subscribe(
      (res: HttpResponse<IPersonalReference[]>) => {
          this.personalReferences = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    
  }

  completeLoanProcess() {
      this.subscribeToSaveResponse(this.loanProcessService.create(this.loanProcess));
  }
  
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
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
  
  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }
  
  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  
  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanProcess>>) {
    result
    .pipe(
       filter((mayBeOk: HttpResponse<ILoanProcess>) => mayBeOk.ok),
       map((response: HttpResponse<ILoanProcess>) => response.body)
    )
    .subscribe((res: ILoanProcess) => {
        this.amortizationSchedule.forEach((item) => {
            item.loanProcessId = res.id;
        });
        this.warranties.forEach((item) => {
            item.loanProcesses = [res];
        });
        //Call other services to save amortization schedule and warranties
        this.amortizationTableService.createMasive(this.amortizationSchedule)
        .pipe(
          filter((mayBeOk: HttpResponse<IAmortizationTable[]>) => mayBeOk.ok),
          map((response: HttpResponse<IAmortizationTable[]>) => response.body)
        )
        .subscribe((res: IAmortizationTable[]) => {
            this.warratyService.createMasive(this.warranties)
            .pipe(
              filter((mayBeOk: HttpResponse<IWarranty[]>) => mayBeOk.ok),
              map((response: HttpResponse<IWarranty[]>) => response.body)
            )
            .subscribe((res: IWarranty[]) => {
                this.onSaveSuccess();
            }, (res: HttpErrorResponse) => this.onError(res.message));
        }, (res: HttpErrorResponse) => this.onError(res.message));
    }
    , (res: HttpErrorResponse) => this.onError(res.message));
  }

  protected onSaveSuccess() {
    this.isSaving = true;
    this.jhiAlertService.success('Operacion realizada con exito', null, null);
    setTimeout(() => {
      this.router.navigate(['/']);
    }, 1500);
  }

}
