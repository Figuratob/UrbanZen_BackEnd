import { ILesson } from 'app/shared/model/lesson.model';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

export interface ITeacher {
  id?: number;
  firstName?: string;
  firstNameEng?: string;
  firstNameRus?: string;
  lastName?: string;
  lastNameEng?: string;
  lastNameRus?: string;
  email?: string;
  phone?: string;
  photoContentType?: string;
  photo?: any;
  about?: string;
  aboutEng?: string;
  aboutRus?: string;
  lessons?: ILesson[];
  lessonTemplates?: ILessonTemplate[];
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public firstName?: string,
    public firstNameEng?: string,
    public firstNameRus?: string,
    public lastName?: string,
    public lastNameEng?: string,
    public lastNameRus?: string,
    public email?: string,
    public phone?: string,
    public photoContentType?: string,
    public photo?: any,
    public about?: string,
    public aboutEng?: string,
    public aboutRus?: string,
    public lessons?: ILesson[],
    public lessonTemplates?: ILessonTemplate[]
  ) {}
}
