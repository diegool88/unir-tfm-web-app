import { Injectable } from '@angular/core';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { ILoanProcess, LoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { IBankingAccount, BankingAccount } from 'app/shared/model/bankMS/banking-account.model';

@Injectable({
  providedIn: 'root'
})
export class WizardService {
  customer?: ICustomer = new Customer();
  steps: any[];
  currentStep?: any;
  newLoanProcess: ILoanProcess;
  amortizationSchedule: IAmortizationTable[] = [];
  warranties: IWarranty[] = [];
  selectedAccount: IBankingAccount;

  constructor() {}

  setLoanProcess(newLoanProcess: ILoanProcess) {
    this.newLoanProcess = newLoanProcess;
  }

  getLoanProcess(): ILoanProcess {
    return this.newLoanProcess;
  }

  setSelectedAccount(selectedAccount: IBankingAccount) {
    this.selectedAccount = selectedAccount;
  }

  getSelectedAccount(): IBankingAccount {
    return this.selectedAccount;
  }

  setAmortizationSchedule(amortizationSchedule: IAmortizationTable[]) {
    this.amortizationSchedule = amortizationSchedule;
  }

  getAmortizationSchedule(): IAmortizationTable[] {
    return this.amortizationSchedule;
  }

  addWarranties(warranty: IWarranty) {
    this.warranties.push(warranty);
  }

  setWarranties(warranties: IWarranty[]) {
    this.warranties = warranties;
  }

  getWarranties(): IWarranty[] {
    return this.warranties;
  }

  getSteps(): any[] {
    if (this.steps === undefined || this.steps.length === 0) {
      this.steps = [
        {
          index: 1,
          label: 'creditApp.customer.detail.title',
          path: ['customer', this.customer.id, 'edit'],
          queryParams: { mode: 'wizard' },
          icon: 'user'
        },
        { index: 2, label: 'creditApp.address.home.title', path: ['address'], queryParams: { mode: 'wizard' }, icon: 'home' },
        {
          index: 3,
          label: 'creditApp.telephoneNumber.home.title',
          path: ['telephone-number'],
          queryParams: { mode: 'wizard' },
          icon: 'phone'
        },
        {
          index: 4,
          label: 'creditApp.personalReference.home.title',
          path: ['personal-reference'],
          queryParams: { mode: 'wizard' },
          icon: 'users'
        },
        {
          index: 5,
          label: 'creditApp.loanMsLoanProcess.home.title',
          path: ['loan-process', 'new'],
          queryParams: { mode: 'wizard' },
          icon: 'th-list'
        },
        {
          index: 6,
          label: 'creditApp.loanMsWarranty.home.title',
          path: ['warranty'],
          queryParams: { mode: 'wizard' },
          icon: 'file-contract'
        },
        {
          index: 7,
          label: 'creditApp.loanMsLoanProcess.home.title',
          path: ['summary'],
          queryParams: { mode: 'wizard' },
          icon: 'file-alt'
        }
      ];
    }
    if (this.customer.id === undefined || this.customer.id === null || this.customer.id === 0) this.steps[0].path = ['customer', 'new'];
    return this.steps;
  }

  setCustomer(customer: ICustomer) {
    this.customer = customer;
  }

  getCurrentStep() {
    this.getSteps();
    return this.currentStep !== undefined ? this.currentStep : this.steps[0];
  }

  setCurrentStep(step: any) {
    this.currentStep = step;
  }

  getCustomer(): ICustomer {
    return this.customer;
  }

  reloadSteps() {
    this.steps = [];
    return this.getSteps();
  }

  clearSteps() {
    this.steps = [];
    this.warranties = [];
    this.amortizationSchedule = [];
    this.newLoanProcess = new LoanProcess();
    this.selectedAccount = new BankingAccount();
  }
}
