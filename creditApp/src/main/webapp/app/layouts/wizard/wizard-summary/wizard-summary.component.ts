import { Component, OnInit } from '@angular/core';
import { ICustomer } from 'app/shared/model/customer.model';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { WizardService } from '../wizard.service';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';

@Component({
  selector: 'jhi-wizard-summary',
  templateUrl: './wizard-summary.component.html',
  styleUrls: ['./wizard-summary.component.scss']
})
export class WizardSummaryComponent implements OnInit {
  customer?: ICustomer;
  amortizationSchedule?: IAmortizationTable[];
  selectedAccount?: IBankingAccount;
  loanProcess?: ILoanProcess;
  warranties?: IWarranty[];

  constructor(protected wizardService: WizardService) {}

  ngOnInit() {
    this.customer = this.wizardService.getCustomer();
    this.amortizationSchedule = this.wizardService.getAmortizationSchedule();
    this.selectedAccount = this.wizardService.getSelectedAccount();
    this.loanProcess = this.wizardService.getLoanProcess();
    this.warranties = this.wizardService.getWarranties();
  }

  completeLoanProcess() {}
}
