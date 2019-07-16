import { Moment } from 'moment';

export const enum TransactionType {
  DEPOSIT = 'DEPOSIT',
  DEBIT = 'DEBIT',
  WITHDRAWAL = 'WITHDRAWAL'
}

export const enum AccountType {
  SAVINGS = 'SAVINGS',
  CHECKING = 'CHECKING'
}

export interface IBankingTransaction {
  id?: number;
  number?: number;
  date?: Moment;
  ammount?: number;
  transactionType?: TransactionType;
  extOriginAccount?: number;
  extOriginAccountType?: AccountType;
  extOriginAccountBank?: string;
  extDestinationAccount?: number;
  extDestinationAccountType?: AccountType;
  extDestinationAccountBank?: string;
  originAccountNumber?: string;
  originAccountId?: number;
  destinationAccountNumber?: string;
  destinationAccountId?: number;
  bankingEntityId?: number;
}

export class BankingTransaction implements IBankingTransaction {
  constructor(
    public id?: number,
    public number?: number,
    public date?: Moment,
    public ammount?: number,
    public transactionType?: TransactionType,
    public extOriginAccount?: number,
    public extOriginAccountType?: AccountType,
    public extOriginAccountBank?: string,
    public extDestinationAccount?: number,
    public extDestinationAccountType?: AccountType,
    public extDestinationAccountBank?: string,
    public originAccountNumber?: string,
    public originAccountId?: number,
    public destinationAccountNumber?: string,
    public destinationAccountId?: number,
    public bankingEntityId?: number
  ) {}
}
