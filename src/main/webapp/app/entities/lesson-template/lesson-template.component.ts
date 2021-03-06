import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';
import { AccountService, JhiLanguageHelper } from 'app/core';
import { LessonTemplateService } from './lesson-template.service';

@Component({
  selector: 'jhi-lesson-template',
  templateUrl: './lesson-template.component.html'
})
export class LessonTemplateComponent implements OnInit, OnDestroy {
  lessonTemplates: ILessonTemplate[];
  currentAccount: any;
  lessonsTemplateModificationSubscriber: Subscription;
  createTimetableSubscriber: Subscription;
  language: any;

  constructor(
    protected lessonTemplateService: LessonTemplateService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService,
    protected languageHelper: JhiLanguageHelper,
    protected languageService: JhiLanguageService
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

  ngOnInit() {
    this.languageHelper.language.subscribe(lang => {
      this.language = lang;
    });
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.createTimetableSubscriber = this.eventManager.subscribe('createTimetableEvent', res => {
      console.log(res.startDate, '  ', res.endDate);
      this.lessonTemplateService.createTimetable(res.startDate, res.endDate).subscribe(
        res => {
          console.log(res.body);
        },
        error => {}
      );
    });
    this.registerChangeInLessonTemplates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.lessonsTemplateModificationSubscriber);
    this.eventManager.destroy(this.createTimetableSubscriber);
  }

  trackId(index: number, item: ILessonTemplate) {
    return item.id;
  }

  registerChangeInLessonTemplates() {
    this.lessonsTemplateModificationSubscriber = this.eventManager.subscribe('lessonTemplateListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
