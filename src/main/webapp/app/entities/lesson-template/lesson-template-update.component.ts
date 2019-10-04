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
  repeatStartDateDp: any;
  repeatUntilDateDp: any;

  editForm = this.fb.group({
    id: [],
    dayOfWeek: [null, [Validators.required]],
    startHour: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
    startMinute: [null, [Validators.required, Validators.min(0), Validators.max(59)]],
    endHour: [null, [Validators.required, Validators.min(0), Validators.max(23)]],
    endMinute: [null, [Validators.required, Validators.min(0), Validators.max(59)]],
    name: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.maxLength(1000)]],
    street: [],
    city: [],
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
      description: lessonTemplate.description,
      street: lessonTemplate.street,
      city: lessonTemplate.city,
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
      description: this.editForm.get(['description']).value,
      street: this.editForm.get(['street']).value,
      city: this.editForm.get(['city']).value,
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
