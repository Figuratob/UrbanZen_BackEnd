import { Moment } from 'moment';
import { ILesson } from 'app/shared/model/lesson.model';
import { ITeacher } from 'app/shared/model/teacher.model';

export const enum DayOfWeek {
  Monday = 'Monday',
  Tuesday = 'Tuesday',
  Wednesday = 'Wednesday',
  Thursday = 'Thursday',
  Friday = 'Friday',
  Saturday = 'Saturday',
  Sunday = 'Sunday'
}

export interface ILessonTemplate {
  id?: number;
  dayOfWeek?: DayOfWeek;
  startHour?: number;
  startMinute?: number;
  endHour?: number;
  endMinute?: number;
  name?: string;
  description?: string;
  street?: string;
  city?: string;
  availableSpaces?: number;
  repeatStartDate?: Moment;
  repeatUntilDate?: Moment;
  lessons?: ILesson[];
  teacher?: ITeacher;
}

export class LessonTemplate implements ILessonTemplate {
  constructor(
    public id?: number,
    public dayOfWeek?: DayOfWeek,
    public startHour?: number,
    public startMinute?: number,
    public endHour?: number,
    public endMinute?: number,
    public name?: string,
    public description?: string,
    public street?: string,
    public city?: string,
    public availableSpaces?: number,
    public repeatStartDate?: Moment,
    public repeatUntilDate?: Moment,
    public lessons?: ILesson[],
    public teacher?: ITeacher
  ) {}
}
