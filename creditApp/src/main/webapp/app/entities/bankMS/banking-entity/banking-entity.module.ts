import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  BankingEntityComponent,
  BankingEntityDetailComponent,
  BankingEntityUpdateComponent,
  BankingEntityDeletePopupComponent,
  BankingEntityDeleteDialogComponent,
  bankingEntityRoute,
  bankingEntityPopupRoute
} from './';

const ENTITY_STATES = [...bankingEntityRoute, ...bankingEntityPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BankingEntityComponent,
    BankingEntityDetailComponent,
    BankingEntityUpdateComponent,
    BankingEntityDeleteDialogComponent,
    BankingEntityDeletePopupComponent
  ],
  entryComponents: [
    BankingEntityComponent,
    BankingEntityUpdateComponent,
    BankingEntityDeleteDialogComponent,
    BankingEntityDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BankMsBankingEntityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
