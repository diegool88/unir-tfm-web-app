import { Moment } from 'moment';

export const enum Genre {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export const enum Relation {
  FRIEND = 'FRIEND',
  EMPLOYEER = 'EMPLOYEER',
  SPOUSE = 'SPOUSE',
  PARTNER = 'PARTNER',
  RELATIVE = 'RELATIVE'
}

export interface IPersonalReference {
  id?: number;
  name?: string;
  lastname?: string;
  genre?: Genre;
  email?: string;
  birthDate?: Moment;
  relation?: Relation;
  customerId?: number;
}

export class PersonalReference implements IPersonalReference {
  constructor(
    public id?: number,
    public name?: string,
    public lastname?: string,
    public genre?: Genre,
    public email?: string,
    public birthDate?: Moment,
    public relation?: Relation,
    public customerId?: number
  ) {}
}
