import { NgModule } from '@angular/core';
import { NgbdModalBasicComponent } from 'app/modal-basic/modal-basic';
import { UrbanZenSharedCommonModule, UrbanZenSharedLibsModule } from 'app/shared';

@NgModule({
  imports: [UrbanZenSharedLibsModule, UrbanZenSharedCommonModule],
  declarations: [NgbdModalBasicComponent],
  entryComponents: [],
  providers: [],
  exports: [NgbdModalBasicComponent]
})
export class NgbdModalBasicModule {}
