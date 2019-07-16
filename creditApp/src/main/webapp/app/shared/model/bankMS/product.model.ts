import { Moment } from 'moment';
import { IFee } from 'app/shared/model/bankMS/fee.model';
import { ICurrency } from 'app/shared/model/bankMS/currency.model';

export const enum ProductCategory {
  CREDIT = 'CREDIT',
  PORTFOLIO = 'PORTFOLIO'
}

export const enum Affirmation {
  YES = 'YES',
  NO = 'NO'
}

export interface IProduct {
  id?: number;
  mnemonic?: string;
  name?: string;
  description?: string;
  category?: ProductCategory;
  startDate?: Moment;
  endDate?: Moment;
  state?: Affirmation;
  interestRate?: number;
  bankingEntityId?: number;
  fees?: IFee[];
  currencies?: ICurrency[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public mnemonic?: string,
    public name?: string,
    public description?: string,
    public category?: ProductCategory,
    public startDate?: Moment,
    public endDate?: Moment,
    public state?: Affirmation,
    public interestRate?: number,
    public bankingEntityId?: number,
    public fees?: IFee[],
    public currencies?: ICurrency[]
  ) {}
}
