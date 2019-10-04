import { ILesson } from 'app/shared/model/lesson.model';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

export interface ITeacher {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  photoContentType?: string;
  photo?: any;
  about?: string;
  lessons?: ILesson[];
  lessonTemplates?: ILessonTemplate[];
}

export class Teacher implements ITeacher {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phone?: string,
    public photoContentType?: string,
    public photo?: any,
    public about?: string,
    public lessons?: ILesson[],
    public lessonTemplates?: ILessonTemplate[]
  ) {}
}
