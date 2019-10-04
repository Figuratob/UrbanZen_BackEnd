import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UrbanZenSharedModule } from 'app/shared';
import {
  LessonTemplateComponent,
  LessonTemplateDetailComponent,
  LessonTemplateUpdateComponent,
  LessonTemplateDeletePopupComponent,
  LessonTemplateDeleteDialogComponent,
  lessonTemplateRoute,
  lessonTemplatePopupRoute
} from './';

const ENTITY_STATES = [...lessonTemplateRoute, ...lessonTemplatePopupRoute];

@NgModule({
  imports: [UrbanZenSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LessonTemplateComponent,
    LessonTemplateDetailComponent,
    LessonTemplateUpdateComponent,
    LessonTemplateDeleteDialogComponent,
    LessonTemplateDeletePopupComponent
  ],
  entryComponents: [
    LessonTemplateComponent,
    LessonTemplateUpdateComponent,
    LessonTemplateDeleteDialogComponent,
    LessonTemplateDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UrbanZenLessonTemplateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
