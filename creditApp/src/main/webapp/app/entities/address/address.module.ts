import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  AddressComponent,
  AddressDetailComponent,
  AddressUpdateComponent,
  AddressDeletePopupComponent,
  AddressDeleteDialogComponent,
  addressRoute,
  addressPopupRoute
} from './';

const ENTITY_STATES = [...addressRoute, ...addressPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AddressComponent,
    AddressDetailComponent,
    AddressUpdateComponent,
    AddressDeleteDialogComponent,
    AddressDeletePopupComponent
  ],
  entryComponents: [AddressComponent, AddressUpdateComponent, AddressDeleteDialogComponent, AddressDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [ AddressComponent, AddressDetailComponent, AddressUpdateComponent, AddressDeleteDialogComponent, AddressDeletePopupComponent ]
})
export class CreditAppAddressModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
