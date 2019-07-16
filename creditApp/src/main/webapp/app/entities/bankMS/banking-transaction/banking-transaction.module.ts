import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  BankingTransactionComponent,
  BankingTransactionDetailComponent,
  BankingTransactionUpdateComponent,
  BankingTransactionDeletePopupComponent,
  BankingTransactionDeleteDialogComponent,
  bankingTransactionRoute,
  bankingTransactionPopupRoute
} from './';

const ENTITY_STATES = [...bankingTransactionRoute, ...bankingTransactionPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BankingTransactionComponent,
    BankingTransactionDetailComponent,
    BankingTransactionUpdateComponent,
    BankingTransactionDeleteDialogComponent,
    BankingTransactionDeletePopupComponent
  ],
  entryComponents: [
    BankingTransactionComponent,
    BankingTransactionUpdateComponent,
    BankingTransactionDeleteDialogComponent,
    BankingTransactionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BankMsBankingTransactionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
