import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILessonTemplate, LessonTemplate } from 'app/shared/model/lesson-template.model';
import { LessonTemplateService } from './lesson-template.service';
import { ITeacher } from 'app/shared/model/teacher.model';
import { TeacherService } from 'app/entities/teacher';

@Component({
  selector: 'jhi-lesson-template-update',
  templateUrl: './lesson-template-update.component.html'
})
export class LessonTemplateUpdateComponent implements OnInit {
  isSaving: boolean;

  teachers: ITeacher[];
  language: any;

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required]],
    startHour: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
    startMinute: [null, [Validators.required, Validators.min(0), Validators.max(59)]],
    endHour: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
    endMinute: [null, [Validators.required, Validators.min(0), Validators.max(59)]],
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
    repeatStartDate: [null, [Validators.required]],
    repeatUntilDate: [],
    teacher: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected lessonTemplateService: LessonTemplateService,
    protected teacherService: TeacherService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lessonTemplate }) => {
      this.updateForm(lessonTemplate);
    });
    this.teacherService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITeacher[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITeacher[]>) => response.body)
      )
      .subscribe((res: ITeacher[]) => (this.teachers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(lessonTemplate: ILessonTemplate) {
    this.editForm.patchValue({
      id: lessonTemplate.id,
      dayOfWeek: lessonTemplate.dayOfWeek,
      startHour: lessonTemplate.startHour,
      startMinute: lessonTemplate.startMinute,
      endHour: lessonTemplate.endHour,
      endMinute: lessonTemplate.endMinute,
      name: lessonTemplate.name,
      nameEng: lessonTemplate.nameEng,
      nameRus: lessonTemplate.nameRus,
      description: lessonTemplate.description,
      descriptionEng: lessonTemplate.descriptionEng,
      descriptionRus: lessonTemplate.descriptionRus,
      street: lessonTemplate.street,
      streetEng: lessonTemplate.streetEng,
      streetRus: lessonTemplate.streetRus,
      city: lessonTemplate.city,
      cityEng: lessonTemplate.city,
      cityRus: lessonTemplate.city,
      availableSpaces: lessonTemplate.availableSpaces,
      repeatStartDate: lessonTemplate.repeatStartDate,
      repeatUntilDate: lessonTemplate.repeatUntilDate,
      teacher: lessonTemplate.teacher
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lessonTemplate = this.createFromForm();
    if (lessonTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.lessonTemplateService.update(lessonTemplate));
    } else {
      this.subscribeToSaveResponse(this.lessonTemplateService.create(lessonTemplate));
    }
  }

  private createFromForm(): ILessonTemplate {
    return {
      ...new LessonTemplate(),
      id: this.editForm.get(['id']).value,
      dayOfWeek: this.editForm.get(['dayOfWeek']).value,
      startHour: this.editForm.get(['startHour']).value,
      startMinute: this.editForm.get(['startMinute']).value,
      endHour: this.editForm.get(['endHour']).value,
      endMinute: this.editForm.get(['endMinute']).value,
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
      repeatStartDate: this.editForm.get(['repeatStartDate']).value,
      repeatUntilDate: this.editForm.get(['repeatUntilDate']).value,
      teacher: this.editForm.get(['teacher']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILessonTemplate>>) {
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
}
