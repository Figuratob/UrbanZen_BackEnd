import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILesson } from 'app/shared/model/lesson.model';
import { AccountService } from 'app/core';
import { LessonService } from './lesson.service';

@Component({
  selector: 'jhi-lesson',
  templateUrl: './lesson.component.html'
})
export class LessonComponent implements OnInit, OnDestroy {
  lessons: ILesson[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lessonService: LessonService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

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

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLessons();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILesson) {
    return item.id;
  }

  registerChangeInLessons() {
    this.eventSubscriber = this.eventManager.subscribe('lessonListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
