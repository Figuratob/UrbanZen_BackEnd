import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILesson } from 'app/shared/model/lesson.model';
import { Booking, IBooking } from 'app/shared/model/booking.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { IUser } from 'app/core';
import { BookingService } from 'app/entities/booking/booking.service';

@Component({
  selector: 'jhi-lesson-detail',
  templateUrl: './lesson-detail.component.html'
})
export class LessonDetailComponent implements OnInit {
  isSaving: boolean;
  users: IUser[];
  lesson: ILesson;

  constructor(protected activatedRoute: ActivatedRoute, protected bookingService: BookingService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lesson }) => {
      this.lesson = lesson;
    });
  }

  previousState() {
    window.history.back();
  }

  book() {
    this.isSaving = true;
    const lessonId = this.lesson.id;
    this.subscribeToSaveResponse(this.bookingService.createBook(lessonId));
  }

  save() {
    this.isSaving = true;
    const booking = this.createBooking();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createBooking(): IBooking {
    return {
      ...new Booking(),
      id: this.lesson.id,
      reservationDate: moment(this.lesson.startDate, DATE_TIME_FORMAT),
      lesson: this.lesson
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
}
