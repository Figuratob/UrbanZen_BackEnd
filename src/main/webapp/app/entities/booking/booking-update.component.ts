import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBooking, Booking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';
import { IUser, UserService } from 'app/core';
import { ILesson } from 'app/shared/model/lesson.model';
import { LessonService } from 'app/entities/lesson';

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html'
})
export class BookingUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  lessons: ILesson[];
  lesson: ILesson;
  editForm = this.fb.group({
    id: [],
    reservationDate: [null, [Validators.required]],
    cancelDate: [],
    user: [],
    lesson: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookingService: BookingService,
    protected userService: UserService,
    protected lessonService: LessonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ booking }) => {
      this.updateForm(booking);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.lessonService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILesson[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILesson[]>) => response.body)
      )
      .subscribe((res: ILesson[]) => (this.lessons = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(booking: IBooking) {
    this.editForm.patchValue({
      id: booking.id,
      reservationDate: booking.reservationDate != null ? booking.reservationDate.format(DATE_TIME_FORMAT) : null,
      cancelDate: booking.cancelDate != null ? booking.cancelDate.format(DATE_TIME_FORMAT) : null,
      user: booking.user,
      lesson: booking.lesson
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  saveBooking() {
    this.isSaving = true;
    const lesson = this.editForm.get(['lesson']).value;
    const lessonId = lesson['id'];
    const bookingId = this.editForm.get(['id']).value;
    this.subscribeToSaveResponse(this.bookingService.updateUserBooking(lessonId, bookingId));
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id']).value,
      reservationDate:
        this.editForm.get(['reservationDate']).value != null
          ? moment(this.editForm.get(['reservationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      cancelDate:
        this.editForm.get(['cancelDate']).value != null ? moment(this.editForm.get(['cancelDate']).value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user']).value,
      lesson: this.editForm.get(['lesson']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackLessonById(index: number, item: ILesson) {
    return item.id;
  }
}
