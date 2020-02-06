import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILessonTemplate } from 'app/shared/model/lesson-template.model';
import { JhiLanguageHelper } from 'app/core';

@Component({
  selector: 'jhi-lesson-template-detail',
  templateUrl: './lesson-template-detail.component.html'
})
export class LessonTemplateDetailComponent implements OnInit {
  lessonTemplate: ILessonTemplate;
  language: any;
  constructor(protected activatedRoute: ActivatedRoute, protected languageHelper: JhiLanguageHelper) {}

  ngOnInit() {
    this.languageHelper.language.subscribe(lang => {
      this.language = lang;
      console.log('lang: ' + this.language);
    });
    this.activatedRoute.data.subscribe(({ lessonTemplate }) => {
      this.lessonTemplate = lessonTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
