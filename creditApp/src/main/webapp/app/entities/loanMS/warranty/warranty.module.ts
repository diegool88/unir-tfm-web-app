import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CreditAppSharedModule } from 'app/shared';
import {
  WarrantyComponent,
  WarrantyDetailComponent,
  WarrantyUpdateComponent,
  WarrantyDeletePopupComponent,
  WarrantyDeleteDialogComponent,
  warrantyRoute,
  warrantyPopupRoute
} from './';
import { WarrantyDeleteDialogTmpComponent, WarrantyDeletePopupTmpComponent } from './warranty-delete-dialog-tmp/warranty-delete-dialog-tmp.component';

const ENTITY_STATES = [...warrantyRoute, ...warrantyPopupRoute];

@NgModule({
  imports: [CreditAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WarrantyComponent,
    WarrantyDetailComponent,
    WarrantyUpdateComponent,
    WarrantyDeleteDialogComponent,
    WarrantyDeletePopupComponent,
    WarrantyDeleteDialogTmpComponent,
    WarrantyDeletePopupTmpComponent
  ],
  entryComponents: [WarrantyComponent, WarrantyUpdateComponent, WarrantyDeleteDialogComponent, WarrantyDeletePopupComponent, WarrantyDeleteDialogTmpComponent, WarrantyDeletePopupTmpComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [WarrantyComponent,
            WarrantyDetailComponent,
            WarrantyUpdateComponent,
            WarrantyDeleteDialogComponent,
            WarrantyDeletePopupComponent,
            WarrantyDeleteDialogTmpComponent,
            WarrantyDeletePopupTmpComponent]
})
export class LoanMsWarrantyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
