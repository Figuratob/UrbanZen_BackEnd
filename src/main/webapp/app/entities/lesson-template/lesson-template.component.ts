import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILessonTemplate } from 'app/shared/model/lesson-template.model';
import { AccountService } from 'app/core';
import { LessonTemplateService } from './lesson-template.service';

@Component({
  selector: 'jhi-lesson-template',
  templateUrl: './lesson-template.component.html'
})
export class LessonTemplateComponent implements OnInit, OnDestroy {
  lessonTemplates: ILessonTemplate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected lessonTemplateService: LessonTemplateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.lessonTemplateService
      .query()
      .pipe(
        filter((res: HttpResponse<ILessonTemplate[]>) => res.ok),
        map((res: HttpResponse<ILessonTemplate[]>) => res.body)
      )
      .subscribe(
        (res: ILessonTemplate[]) => {
          this.lessonTemplates = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  createTimetable() {
    this.lessonTemplateService.createTimetable().subscribe(
      (res: any) => {
        console.log(res);
      },
      (res: HttpErrorResponse) => {
        console.log(res);
      }
    );
  }

  openDialog() {
    // this.lessonTemplateService
    //   .openDialog()
    //   .subscribe(
    //     (res: any) => {
    //       console.log(res);
    //     },
    //     (res: HttpErrorResponse) => {
    //       console.log(res);
    //     }
    //   );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLessonTemplates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILessonTemplate) {
    return item.id;
  }

  registerChangeInLessonTemplates() {
    this.eventSubscriber = this.eventManager.subscribe('lessonTemplateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
