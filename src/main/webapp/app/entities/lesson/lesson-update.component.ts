import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILesson, Lesson } from 'app/shared/model/lesson.model';
import { LessonService } from './lesson.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';
import { LessonTemplateService } from 'app/entities/lesson-template';

@Component({
  selector: 'jhi-lesson-update',
  templateUrl: './lesson-update.component.html'
})
export class LessonUpdateComponent implements OnInit {
  isSaving: boolean;

  teachers: ITeacher[];

  lessontemplates: ILessonTemplate[];

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    name: [null, [Validators.required]],
    nameEng: [null, [Validators.required]],
    nameRus: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    descriptionEng: [null, [Validators.required, Validators.maxLength(1000)]],
    descriptionRus: [null, [Validators.required, Validators.maxLength(1000)]],
    street: [],
    streetEng: [],
    streetRus: [],
    city: [],
    cityEng: [],
    cityRus: [],
    availableSpaces: [null, [Validators.required, Validators.min(0)]],
    teacher: [null, Validators.required],
    lessonTemplate: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected lessonService: LessonService,
    protected teacherService: TeacherService,
    protected lessonTemplateService: LessonTemplateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lesson }) => {
      this.updateForm(lesson);
    });
    this.teacherService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITeacher[]>) => response.body)
      )
      .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.lessonTemplateService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILessonTemplate[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILessonTemplate[]>) => response.body)
      )
      .subscribe((res: ILessonTemplate[]) => (this.lessontemplates = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(lesson: ILesson) {
    this.editForm.patchValue({
      id: lesson.id,
      startDate: lesson.startDate != null ? lesson.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: lesson.endDate != null ? lesson.endDate.format(DATE_TIME_FORMAT) : null,
      name: lesson.name,
      nameEng: lesson.nameEng,
      nameRus: lesson.nameRus,
      description: lesson.description,
      descriptionEng: lesson.descriptionEng,
      descriptionRus: lesson.descriptionRus,
      street: lesson.street,
      streetEng: lesson.streetEng,
      streetRus: lesson.streetRus,
      city: lesson.city,
      cityEng: lesson.cityEng,
      cityRus: lesson.cityRus,
      availableSpaces: lesson.availableSpaces,
      teacher: lesson.teacher,
      lessonTemplate: lesson.lessonTemplate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lesson = this.createFromForm();
    if (lesson.id !== undefined) {
      this.subscribeToSaveResponse(this.lessonService.update(lesson));
    } else {
      this.subscribeToSaveResponse(this.lessonService.create(lesson));
    }
  }

  private createFromForm(): ILesson {
    return {
      ...new Lesson(),
      id: this.editForm.get(['id']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      name: this.editForm.get(['name']).value,
      nameEng: this.editForm.get(['nameEng']).value,
      nameRus: this.editForm.get(['nameRus']).value,
      description: this.editForm.get(['description']).value,
      descriptionEng: this.editForm.get(['descriptionEng']).value,
      descriptionRus: this.editForm.get(['descriptionRus']).value,
      street: this.editForm.get(['street']).value,
      streetEng: this.editForm.get(['streetEng']).value,
      streetRus: this.editForm.get(['streetRus']).value,
      city: this.editForm.get(['city']).value,
      cityEng: this.editForm.get(['cityEng']).value,
      cityRus: this.editForm.get(['cityRus']).value,
      availableSpaces: this.editForm.get(['availableSpaces']).value,
      teacher: this.editForm.get(['teacher']).value,
      lessonTemplate: this.editForm.get(['lessonTemplate']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILesson>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTeacherById(index: number, item: ITeacher) {
    return item.id;
  }

  trackLessonTemplateById(index: number, item: ILessonTemplate) {
    return item.id;
  }
}
