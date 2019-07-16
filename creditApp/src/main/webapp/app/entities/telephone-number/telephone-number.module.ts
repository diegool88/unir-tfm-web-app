import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  TelephoneNumberComponent,
  TelephoneNumberDetailComponent,
  TelephoneNumberUpdateComponent,
  TelephoneNumberDeletePopupComponent,
  TelephoneNumberDeleteDialogComponent,
  telephoneNumberRoute,
  telephoneNumberPopupRoute
} from './';

const ENTITY_STATES = [...telephoneNumberRoute, ...telephoneNumberPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TelephoneNumberComponent,
    TelephoneNumberDetailComponent,
    TelephoneNumberUpdateComponent,
    TelephoneNumberDeleteDialogComponent,
    TelephoneNumberDeletePopupComponent
  ],
  entryComponents: [
    TelephoneNumberComponent,
    TelephoneNumberUpdateComponent,
    TelephoneNumberDeleteDialogComponent,
    TelephoneNumberDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppTelephoneNumberModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
