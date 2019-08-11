import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import { CreditAppCustomerModule } from 'app/entities/customer/customer.module';
import { CreditAppAddressModule } from 'app/entities/address/address.module';
import { CreditAppTelephoneNumberModule } from 'app/entities/telephone-number/telephone-number.module';
import { CreditAppPersonalReferenceModule } from 'app/entities/personal-reference/personal-reference.module';
import { WizardComponent } from './wizard.component';
import { wizardRoute  } from './wizard.route';
import { WizardMainComponent } from './wizard-main/wizard-main.component';
import { WizardNavbarComponent } from './wizard-main/wizard-navbar/wizard-navbar.component';
import { WizardFooterComponent } from './wizard-main/wizard-footer/wizard-footer.component';
import { LoanMsLoanProcessModule } from "app/entities/loanMS/loan-process/loan-process.module";
import { LoanMsWarrantyModule } from "app/entities/loanMS/warranty/warranty.module";

const ENTITY_STATES = [ ...wizardRoute ];

@NgModule({
  imports: [CreditAppSharedModule,
            CreditAppCustomerModule,
            CreditAppAddressModule,
            CreditAppTelephoneNumberModule,
            CreditAppPersonalReferenceModule,
            LoanMsLoanProcessModule,
            LoanMsWarrantyModule,
            RouterModule.forChild(ENTITY_STATES)
  ],
  declarations: [
    WizardComponent,
    WizardMainComponent,
    WizardNavbarComponent,
    WizardFooterComponent
  ],
  bootstrap: [ WizardComponent ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppWizardModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
