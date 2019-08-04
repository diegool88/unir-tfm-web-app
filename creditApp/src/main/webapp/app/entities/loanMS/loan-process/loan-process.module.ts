import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  LoanProcessComponent,
  LoanProcessDetailComponent,
  LoanProcessUpdateComponent,
  LoanProcessDeletePopupComponent,
  LoanProcessDeleteDialogComponent,
  loanProcessRoute,
  loanProcessPopupRoute
} from './';

const ENTITY_STATES = [...loanProcessRoute, ...loanProcessPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LoanProcessComponent,
    LoanProcessDetailComponent,
    LoanProcessUpdateComponent,
    LoanProcessDeleteDialogComponent,
    LoanProcessDeletePopupComponent
  ],
  entryComponents: [LoanProcessComponent, LoanProcessUpdateComponent, LoanProcessDeleteDialogComponent, LoanProcessDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ LoanProcessUpdateComponent ]
})
export class LoanMsLoanProcessModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
