export interface ITelephoneNumber {
  id?: number;
  countryCode?: number;
  regionCode?: number;
  number?: number;
  addressId?: number;
}

export class TelephoneNumber implements ITelephoneNumber {
  constructor(
    public id?: number,
    public countryCode?: number,
    public regionCode?: number,
    public number?: number,
    public addressId?: number
  ) {}
}
