import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

@Component({
  selector: 'jhi-lesson-template-detail',
  templateUrl: './lesson-template-detail.component.html'
})
export class LessonTemplateDetailComponent implements OnInit {
  lessonTemplate: ILessonTemplate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lessonTemplate }) => {
      this.lessonTemplate = lessonTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
