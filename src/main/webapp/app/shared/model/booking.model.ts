import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ILesson } from 'app/shared/model/lesson.model';

export interface IBooking {
  id?: number;
  reservationDate?: Moment;
  cancelDate?: Moment;
  user?: IUser;
  lesson?: ILesson;
}

export class Booking implements IBooking {
  constructor(
    public id?: number,
    public reservationDate?: Moment,
    public cancelDate?: Moment,
    public user?: IUser,
    public lesson?: ILesson
  ) {}
}
