import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'warranty',
        loadChildren: './loanMS/warranty/warranty.module#LoanMsWarrantyModule'
      },
      {
        path: 'amortization-table',
        loadChildren: './loanMS/amortization-table/amortization-table.module#LoanMsAmortizationTableModule'
      },
      {
        path: 'loan-process',
        loadChildren: './loanMS/loan-process/loan-process.module#LoanMsLoanProcessModule'
      },
      {
        path: 'product',
        loadChildren: './bankMS/product/product.module#BankMsProductModule'
      },
      {
        path: 'fee',
        loadChildren: './bankMS/fee/fee.module#BankMsFeeModule'
      },
      {
        path: 'currency',
        loadChildren: './bankMS/currency/currency.module#BankMsCurrencyModule'
      },
      {
        path: 'banking-entity',
        loadChildren: './bankMS/banking-entity/banking-entity.module#BankMsBankingEntityModule'
      },
      {
        path: 'banking-account',
        loadChildren: './bankMS/banking-account/banking-account.module#BankMsBankingAccountModule'
      },
      {
        path: 'banking-transaction',
        loadChildren: './bankMS/banking-transaction/banking-transaction.module#BankMsBankingTransactionModule'
      },
      {
        path: 'country',
        loadChildren: './regionMS/country/country.module#RegionMsCountryModule'
      },
      {
        path: 'state-province',
        loadChildren: './regionMS/state-province/state-province.module#RegionMsStateProvinceModule'
      },
      {
        path: 'city',
        loadChildren: './regionMS/city/city.module#RegionMsCityModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#CreditAppCustomerModule'
      },
      {
        path: 'personal-reference',
        loadChildren: './personal-reference/personal-reference.module#CreditAppPersonalReferenceModule'
      },
      {
        path: 'address',
        loadChildren: './address/address.module#CreditAppAddressModule'
      },
      {
        path: 'telephone-number',
        loadChildren: './telephone-number/telephone-number.module#CreditAppTelephoneNumberModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppEntityModule {}
