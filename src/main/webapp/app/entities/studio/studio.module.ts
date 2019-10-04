import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UrbanZenSharedModule } from 'app/shared';
import {
  StudioComponent,
  StudioDetailComponent,
  StudioUpdateComponent,
  StudioDeletePopupComponent,
  StudioDeleteDialogComponent,
  studioRoute,
  studioPopupRoute
} from './';

const ENTITY_STATES = [...studioRoute, ...studioPopupRoute];

@NgModule({
  imports: [UrbanZenSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [StudioComponent, StudioDetailComponent, StudioUpdateComponent, StudioDeleteDialogComponent, StudioDeletePopupComponent],
  entryComponents: [StudioComponent, StudioUpdateComponent, StudioDeleteDialogComponent, StudioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UrbanZenStudioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
