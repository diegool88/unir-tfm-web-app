import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  PersonalReferenceComponent,
  PersonalReferenceDetailComponent,
  PersonalReferenceUpdateComponent,
  PersonalReferenceDeletePopupComponent,
  PersonalReferenceDeleteDialogComponent,
  personalReferenceRoute,
  personalReferencePopupRoute
} from './';

const ENTITY_STATES = [...personalReferenceRoute, ...personalReferencePopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonalReferenceComponent,
    PersonalReferenceDetailComponent,
    PersonalReferenceUpdateComponent,
    PersonalReferenceDeleteDialogComponent,
    PersonalReferenceDeletePopupComponent
  ],
  entryComponents: [
    PersonalReferenceComponent,
    PersonalReferenceUpdateComponent,
    PersonalReferenceDeleteDialogComponent,
    PersonalReferenceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppPersonalReferenceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
