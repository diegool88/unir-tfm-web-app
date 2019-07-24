import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import { CreditAppCustomerModule } from 'app/entities/customer/customer.module';
//import { CustomerComponent, CustomerDeleteDialogComponent, CustomerDeletePopupComponent, CustomerUpdateComponent, CustomerDetailComponent } from 'app/entities/customer';
//import { addressRoute, addressPopupRoute } from 'app/entities/address';
//import { personalReferenceRoute, personalReferencePopupRoute } from 'app/entities/personal-reference';
import { WizardComponent } from './wizard.component';
import { wizardRoute  } from './wizard.route';
import { WizardMainComponent } from './wizard-main/wizard-main.component';
import { WizardNavbarComponent } from './wizard-main/wizard-navbar/wizard-navbar.component';

//const ENTITY_STATES = [...customerRoute, ...customerPopupRoute, ...addressRoute, ...addressPopupRoute, ...personalReferenceRoute, ...personalReferencePopupRoute];
const ENTITY_STATES = [ ...wizardRoute ];

@NgModule({
  imports: [CreditAppSharedModule, CreditAppCustomerModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WizardComponent,
    WizardMainComponent,
    WizardNavbarComponent
  ],
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
