import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { JhiLanguageHelper } from 'app/core';

@Component({
  selector: 'jhi-teacher-detail',
  templateUrl: './teacher-detail.component.html'
})
export class TeacherDetailComponent implements OnInit {
  teacher: ITeacher;
  language: any;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected languageHelper: JhiLanguageHelper) {}

  ngOnInit() {
    this.languageHelper.language.subscribe(lang => {
      this.language = lang;
    });
    this.activatedRoute.data.subscribe(({ teacher }) => {
      this.teacher = teacher;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
