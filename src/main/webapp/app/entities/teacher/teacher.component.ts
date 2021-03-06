import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITeacher } from 'app/shared/model/teacher.model';
import { AccountService, JhiLanguageHelper } from 'app/core';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher',
  templateUrl: './teacher.component.html'
})
export class TeacherComponent implements OnInit, OnDestroy {
  teachers: ITeacher[];
  currentAccount: any;
  eventSubscriber: Subscription;
  language: any;

  constructor(
    protected teacherService: TeacherService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected languageHelper: JhiLanguageHelper
  ) {}

  loadAll() {
    this.teacherService
      .query()
      .pipe(
        filter((res: HttpResponse<ITeacher[]>) => res.ok),
        map((res: HttpResponse<ITeacher[]>) => res.body)
      )
      .subscribe(
        (res: ITeacher[]) => {
          this.teachers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.languageHelper.language.subscribe(lang => {
      this.language = lang;
    });
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTeachers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITeacher) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInTeachers() {
    this.eventSubscriber = this.eventManager.subscribe('teacherListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
