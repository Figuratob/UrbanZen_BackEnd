import { NgModule } from '@angular/core';
import { NgbdModalBasic } from 'app/modal-basic/modal-basic';
import { UrbanZenSharedCommonModule, UrbanZenSharedLibsModule } from 'app/shared';

@NgModule({
  imports: [UrbanZenSharedLibsModule, UrbanZenSharedCommonModule],
  declarations: [NgbdModalBasic],
  entryComponents: [],
  providers: [],
  exports: [NgbdModalBasic]
})
export class NgbdModalBasicModule {}
