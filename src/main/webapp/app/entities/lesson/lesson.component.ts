import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILesson } from 'app/shared/model/lesson.model';
import { AccountService, JhiLanguageHelper } from 'app/core';
import { LessonService } from './lesson.service';
import { Moment } from 'moment';
import * as moment from 'moment';

@Component({
  selector: 'jhi-lesson',
  templateUrl: './lesson.component.html'
})
export class LessonComponent implements OnInit, OnDestroy {
  lessons: ILesson[];
  currentAccount: any;
  eventSubscriber: Subscription;
  firstDayOfWeek: Moment;
  lastDayOfWeek: Moment;
  language: any;

  constructor(
    protected lessonService: LessonService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected languageHelper: JhiLanguageHelper
  ) {}

  // ngOnInit() {
  //   this.loadAll();
  //   this.accountService.identity().then(account => {
  //     this.currentAccount = account;
  //   });
  //   this.registerChangeInLessons();
  // }

  ngOnInit() {
    this.languageHelper.language.subscribe(lang => {
      this.language = lang;
    });
    const now = moment();
    const thisMonday = moment(now).startOf('isoWeek');
    const thisSunday = moment(now).endOf('isoWeek');

    this.firstDayOfWeek = thisMonday;
    this.lastDayOfWeek = thisSunday;

    this.refreshLessons(thisMonday, thisSunday);
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLessons();
  }

  loadAll() {
    this.lessonService
      .query()
      .pipe(
        filter((res: HttpResponse<ILesson[]>) => res.ok),
        map((res: HttpResponse<ILesson[]>) => res.body)
      )
      .subscribe(
        (res: ILesson[]) => {
          this.lessons = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
  public refreshLessons(firstDayOfWeek: Moment, lastDayOfWeek: Moment) {
    this.lessonService
      .getData(firstDayOfWeek, lastDayOfWeek)
      .pipe(
        filter((res: HttpResponse<ILesson[]>) => res.ok),
        map((res: HttpResponse<ILesson[]>) => res.body)
      )
      .subscribe(
        (res: ILesson[]) => {
          this.lessons = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILesson) {
    return item.id;
  }

  // registerChangeInLessons() {
  //
  //   this.eventSubscriber = this.eventManager.subscribe('lessonListModification', response =>
  //     this.loadAll());
  // }

  registerChangeInLessons() {
    this.eventSubscriber = this.eventManager.subscribe('lessonListModification', response =>
      this.refreshLessons(this.firstDayOfWeek, this.lastDayOfWeek)
    );
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  previousWeek() {
    this.firstDayOfWeek = moment(this.firstDayOfWeek).subtract(7, 'days');
    this.lastDayOfWeek = moment(this.lastDayOfWeek).subtract(7, 'days');

    this.refreshLessons(this.firstDayOfWeek, this.lastDayOfWeek);
  }

  nextWeek() {
    this.firstDayOfWeek = moment(this.firstDayOfWeek).add(7, 'days');
    this.lastDayOfWeek = moment(this.lastDayOfWeek).add(7, 'days');

    this.refreshLessons(this.firstDayOfWeek, this.lastDayOfWeek);
  }
}
