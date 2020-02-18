import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { ITeacher } from 'app/shared/model/teacher.model';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

export interface ILesson {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  name?: string;
  nameEng?: string;
  nameRus?: string;
  description?: string;
  descriptionEng?: string;
  descriptionRus?: string;
  street?: string;
  streetEng?: string;
  streetRus?: string;
  city?: string;
  cityEng?: string;
  cityRus?: string;
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
    public nameEng?: string,
    public nameRus?: string,
    public description?: string,
    public descriptionEng?: string,
    public descriptionRus?: string,
    public street?: string,
    public streetEng?: string,
    public streetRus?: string,
    public city?: string,
    public cityEng?: string,
    public cityRus?: string,
    public availableSpaces?: number,
    public remainSpaces?: number,
    public bookings?: IBooking[],
    public teacher?: ITeacher,
    public lessonTemplate?: ILessonTemplate
  ) {}
}
