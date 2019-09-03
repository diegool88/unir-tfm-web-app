import { Moment } from 'moment';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';

export const enum LoanProcessStatus {
  APPROVED = 'APPROVED',
  DENIED = 'DENIED',
  PENDING = 'PENDING'
}

export interface ILoanProcess {
  id?: number;
  name?: string;
  requestedAmount?: number;
  givenAmount?: number;
  loanPeriod?: number;
  startDate?: Moment;
  endDate?: Moment;
  justification?: string;
  debtorIdentification?: string;
  debtorIdentificationType?: string;
  debtorCountry?: string;
  bankingEntityMnemonic?: string;
  bankingProductMnemonic?: string;
  rulesEngineResult?: boolean;
  bankingAccountNumber?: number;
  bankingAccountType?: string;
  bankingAccountEntityMnemonic?: string;
  loanProcessStatus?: LoanProcessStatus;
  warranties?: IWarranty[];
}

export class LoanProcess implements ILoanProcess {
  constructor(
    public id?: number,
    public name?: string,
    public requestedAmount?: number,
    public givenAmount?: number,
    public loanPeriod?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public justification?: string,
    public debtorIdentification?: string,
    public debtorIdentificationType?: string,
    public debtorCountry?: string,
    public bankingEntityMnemonic?: string,
    public bankingProductMnemonic?: string,
    public rulesEngineResult?: boolean,
    public bankingAccountNumber?: number,
    public bankingAccountType?: string,
    public bankingAccountEntityMnemonic?: string,
    public loanProcessStatus?: LoanProcessStatus,
    public warranties?: IWarranty[]
  ) {
    this.rulesEngineResult = this.rulesEngineResult || false;
  }
}
