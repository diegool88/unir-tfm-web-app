import { Moment } from 'moment';

export const enum IdentificationType {
  ID = 'ID',
  PASSPORT = 'PASSPORT'
}

export const enum Genre {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export interface ICustomer {
  id?: number;
  firstname?: string;
  secondName?: string;
  lastname?: string;
  secondLastname?: string;
  identificationType?: IdentificationType;
  identificationNumber?: string;
  genre?: Genre;
  email?: string;
  birthDate?: Moment;
  country?: string;
  clientSince?: Moment;
  monthlyIncome?: number;
  userLogin?: string;
  userId?: number;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstname?: string,
    public secondName?: string,
    public lastname?: string,
    public secondLastname?: string,
    public identificationType?: IdentificationType,
    public identificationNumber?: string,
    public genre?: Genre,
    public email?: string,
    public birthDate?: Moment,
    public country?: string,
    public clientSince?: Moment,
    public monthlyIncome?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
