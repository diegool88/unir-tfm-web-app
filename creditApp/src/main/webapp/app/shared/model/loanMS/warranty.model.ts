import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';

export interface IWarranty {
  id?: number;
  name?: string;
  description?: string;
  value?: number;
  documentContentType?: string;
  document?: any;
  debtorIdentification?: string;
  debtorIdentificationType?: string;
  debtorCountry?: string;
  loanProcesses?: ILoanProcess[];
}

export class Warranty implements IWarranty {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public value?: number,
    public documentContentType?: string,
    public document?: any,
    public debtorIdentification?: string,
    public debtorIdentificationType?: string,
    public debtorCountry?: string,
    public loanProcesses?: ILoanProcess[]
  ) {}
}
