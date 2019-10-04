import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStudio, Studio } from 'app/shared/model/studio.model';
import { StudioService } from './studio.service';

@Component({
  selector: 'jhi-studio-update',
  templateUrl: './studio-update.component.html'
})
export class StudioUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    about: [null, [Validators.required]],
    street: [null, [Validators.required]],
    postCode: [null, [Validators.required, Validators.maxLength(5)]],
    city: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phone: [null, [Validators.required]]
  });

  constructor(protected studioService: StudioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ studio }) => {
      this.updateForm(studio);
    });
  }

  updateForm(studio: IStudio) {
    this.editForm.patchValue({
      id: studio.id,
      about: studio.about,
      street: studio.street,
      postCode: studio.postCode,
      city: studio.city,
      email: studio.email,
      phone: studio.phone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const studio = this.createFromForm();
    if (studio.id !== undefined) {
      this.subscribeToSaveResponse(this.studioService.update(studio));
    } else {
      this.subscribeToSaveResponse(this.studioService.create(studio));
    }
  }

  private createFromForm(): IStudio {
    return {
      ...new Studio(),
      id: this.editForm.get(['id']).value,
      about: this.editForm.get(['about']).value,
      street: this.editForm.get(['street']).value,
      postCode: this.editForm.get(['postCode']).value,
      city: this.editForm.get(['city']).value,
      email: this.editForm.get(['email']).value,
      phone: this.editForm.get(['phone']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudio>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
