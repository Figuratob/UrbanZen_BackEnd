import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITeacher, Teacher } from 'app/shared/model/teacher.model';
import { TeacherService } from './teacher.service';

@Component({
  selector: 'jhi-teacher-update',
  templateUrl: './teacher-update.component.html'
})
export class TeacherUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    firstNameEng: [null, [Validators.required]],
    firstNameRus: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    lastNameEng: [null, [Validators.required]],
    lastNameRus: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    about: [],
    aboutEng: [],
    aboutRus: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected teacherService: TeacherService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ teacher }) => {
      this.updateForm(teacher);
    });
  }

  updateForm(teacher: ITeacher) {
    this.editForm.patchValue({
      id: teacher.id,
      firstName: teacher.firstName,
      firstNameEng: teacher.firstNameEng,
      firstNameRus: teacher.firstNameRus,
      lastName: teacher.lastName,
      lastNameEng: teacher.lastNameEng,
      lastNameRus: teacher.lastNameRus,
      email: teacher.email,
      phone: teacher.phone,
      photo: teacher.photo,
      photoContentType: teacher.photoContentType,
      about: teacher.about,
      aboutEng: teacher.aboutEng,
      aboutRus: teacher.aboutRus
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const teacher = this.createFromForm();
    if (teacher.id !== undefined) {
      this.subscribeToSaveResponse(this.teacherService.update(teacher));
    } else {
      this.subscribeToSaveResponse(this.teacherService.create(teacher));
    }
  }

  private createFromForm(): ITeacher {
    return {
      ...new Teacher(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      firstNameEng: this.editForm.get(['firstNameEng']).value,
      firstNameRus: this.editForm.get(['firstNameRus']).value,
      lastName: this.editForm.get(['lastName']).value,
      lastNameEng: this.editForm.get(['lastNameEng']).value,
      lastNameRus: this.editForm.get(['lastNameRus']).value,
      email: this.editForm.get(['email']).value,
      phone: this.editForm.get(['phone']).value,
      photoContentType: this.editForm.get(['photoContentType']).value,
      photo: this.editForm.get(['photo']).value,
      about: this.editForm.get(['about']).value,
      aboutEng: this.editForm.get(['aboutEng']).value,
      aboutRus: this.editForm.get(['aboutRus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeacher>>) {
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
}
