import { Moment } from 'moment';

export interface IAmortizationTable {
  id?: number;
  order?: number;
  dueDate?: Moment;
  amount?: number;
  interest?: number;
  loanProcessId?: number;
}

export class AmortizationTable implements IAmortizationTable {
  constructor(
    public id?: number,
    public order?: number,
    public dueDate?: Moment,
    public amount?: number,
    public interest?: number,
    public loanProcessId?: number
  ) {}
}
