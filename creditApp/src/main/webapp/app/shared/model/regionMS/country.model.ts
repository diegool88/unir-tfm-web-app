export interface ICountry {
  id?: number;
  mnemonic?: string;
  name?: string;
}

export class Country implements ICountry {
  constructor(public id?: number, public mnemonic?: string, public name?: string) {}
}
