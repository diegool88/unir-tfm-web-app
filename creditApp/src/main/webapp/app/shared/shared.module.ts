import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CreditAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

import {JhMaterialModule} from 'app/shared/jh-material.module';
@NgModule({
  imports: [JhMaterialModule, CreditAppSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhMaterialModule, CreditAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppSharedModule {
  static forRoot() {
    return {
      ngModule: CreditAppSharedModule
    };
  }
}
