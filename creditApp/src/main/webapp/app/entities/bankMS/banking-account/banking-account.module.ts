import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  BankingAccountComponent,
  BankingAccountDetailComponent,
  BankingAccountUpdateComponent,
  BankingAccountDeletePopupComponent,
  BankingAccountDeleteDialogComponent,
  bankingAccountRoute,
  bankingAccountPopupRoute
} from './';

const ENTITY_STATES = [...bankingAccountRoute, ...bankingAccountPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BankingAccountComponent,
    BankingAccountDetailComponent,
    BankingAccountUpdateComponent,
    BankingAccountDeleteDialogComponent,
    BankingAccountDeletePopupComponent
  ],
  entryComponents: [
    BankingAccountComponent,
    BankingAccountUpdateComponent,
    BankingAccountDeleteDialogComponent,
    BankingAccountDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BankMsBankingAccountModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
