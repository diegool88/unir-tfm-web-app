import { IProduct } from 'app/shared/model/bankMS/product.model';

export interface IFee {
  id?: number;
  mnemonic?: string;
  name?: string;
  description?: string;
  interestRate?: number;
  uniqueValue?: number;
  products?: IProduct[];
}

export class Fee implements IFee {
  constructor(
    public id?: number,
    public mnemonic?: string,
    public name?: string,
    public description?: string,
    public interestRate?: number,
    public uniqueValue?: number,
    public products?: IProduct[]
  ) {}
}
