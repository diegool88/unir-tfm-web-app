import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  StateProvinceComponent,
  StateProvinceDetailComponent,
  StateProvinceUpdateComponent,
  StateProvinceDeletePopupComponent,
  StateProvinceDeleteDialogComponent,
  stateProvinceRoute,
  stateProvincePopupRoute
} from './';

const ENTITY_STATES = [...stateProvinceRoute, ...stateProvincePopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StateProvinceComponent,
    StateProvinceDetailComponent,
    StateProvinceUpdateComponent,
    StateProvinceDeleteDialogComponent,
    StateProvinceDeletePopupComponent
  ],
  entryComponents: [
    StateProvinceComponent,
    StateProvinceUpdateComponent,
    StateProvinceDeleteDialogComponent,
    StateProvinceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RegionMsStateProvinceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
