export interface IBankingEntity {
  id?: number;
  mnemonic?: string;
  name?: string;
  description?: string;
}

export class BankingEntity implements IBankingEntity {
  constructor(public id?: number, public mnemonic?: string, public name?: string, public description?: string) {}
}
