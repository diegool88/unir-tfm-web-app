import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CreditAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CreditAppSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CreditAppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CreditAppSharedModule {
  static forRoot() {
    return {
      ngModule: CreditAppSharedModule
    };
  }
}
