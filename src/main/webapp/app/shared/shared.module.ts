import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UrbanZenSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [UrbanZenSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [UrbanZenSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UrbanZenSharedModule {
  static forRoot() {
    return {
      ngModule: UrbanZenSharedModule
    };
  }
}
