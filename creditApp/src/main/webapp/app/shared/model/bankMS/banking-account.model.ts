export const enum AccountType {
  SAVINGS = 'SAVINGS',
  CHECKING = 'CHECKING'
}

export interface IBankingAccount {
  id?: number;
  number?: number;
  accountType?: AccountType;
  currentBalance?: number;
  availableBalance?: number;
  customerIdentification?: string;
  customerIdentificationType?: string;
  customerCountry?: string;
  bankingEntityMnemonic?: string;
}

export class BankingAccount implements IBankingAccount {
  constructor(
    public id?: number,
    public number?: number,
    public accountType?: AccountType,
    public currentBalance?: number,
    public availableBalance?: number,
    public customerIdentification?: string,
    public customerIdentificationType?: string,
    public customerCountry?: string,
    public bankingEntityMnemonic?: string
  ) {}
}
