import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  AmortizationTableComponent,
  AmortizationTableDetailComponent,
  AmortizationTableUpdateComponent,
  AmortizationTableDeletePopupComponent,
  AmortizationTableDeleteDialogComponent,
  amortizationTableRoute,
  amortizationTablePopupRoute
} from './';

const ENTITY_STATES = [...amortizationTableRoute, ...amortizationTablePopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AmortizationTableComponent,
    AmortizationTableDetailComponent,
    AmortizationTableUpdateComponent,
    AmortizationTableDeleteDialogComponent,
    AmortizationTableDeletePopupComponent
  ],
  entryComponents: [
    AmortizationTableComponent,
    AmortizationTableUpdateComponent,
    AmortizationTableDeleteDialogComponent,
    AmortizationTableDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LoanMsAmortizationTableModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
