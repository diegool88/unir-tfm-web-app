import { IProduct } from 'app/shared/model/bankMS/product.model';

export interface ICurrency {
  id?: number;
  name?: string;
  sign?: string;
  products?: IProduct[];
}

export class Currency implements ICurrency {
  constructor(public id?: number, public name?: string, public sign?: string, public products?: IProduct[]) {}
}
