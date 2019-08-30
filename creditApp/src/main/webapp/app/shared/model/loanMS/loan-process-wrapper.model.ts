import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';

export interface ILoanProcessWrapper {
    warranties?: IWarranty[];
    loanProcess?: ILoanProcess;
    amortizationSchedule?: IAmortizationTable[];
}

export class LoanProcessWrapper implements ILoanProcessWrapper {
   constructor(
     public warranties?: IWarranty[],
     public loanProcess?: ILoanProcess,
     public amortizationSchedule?: IAmortizationTable[]
   ) {}
}