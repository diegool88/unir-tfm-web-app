export const enum AddressType {
  HOME = 'HOME',
  WORK = 'WORK',
  OTHER = 'OTHER'
}

export interface IAddress {
  id?: number;
  mainStreet?: string;
  secondaryStreet?: string;
  number?: string;
  city?: string;
  province?: string;
  country?: string;
  postalCode?: number;
  addressType?: AddressType;
  customerId?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public mainStreet?: string,
    public secondaryStreet?: string,
    public number?: string,
    public city?: string,
    public province?: string,
    public country?: string,
    public postalCode?: number,
    public addressType?: AddressType,
    public customerId?: number
  ) {}
}
