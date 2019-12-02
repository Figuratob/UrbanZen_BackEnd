import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

export interface ILesson {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  name?: string;
  description?: string;
  street?: string;
  city?: string;
  availableSpaces?: number;
  remainSpaces?: number;
  bookings?: IBooking[];
  teacher?: ITeacher;
  lessonTemplate?: ILessonTemplate;
}

export class Lesson implements ILesson {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public name?: string,
    public description?: string,
    public street?: string,
    public city?: string,
    public availableSpaces?: number,
    public remainSpaces?: number,
    public bookings?: IBooking[],
    public teacher?: ITeacher,
    public lessonTemplate?: ILessonTemplate
  ) {}
}
