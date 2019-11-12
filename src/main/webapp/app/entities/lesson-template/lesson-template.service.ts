import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILessonTemplate } from 'app/shared/model/lesson-template.model';

type EntityResponseType = HttpResponse<ILessonTemplate>;
type EntityArrayResponseType = HttpResponse<ILessonTemplate[]>;

@Injectable({ providedIn: 'root' })
export class LessonTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/lesson-templates';

  constructor(protected http: HttpClient) {}

  create(lessonTemplate: ILessonTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lessonTemplate);
    return this.http
      .post<ILessonTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  createTimetable(): Observable<EntityResponseType> {
    return this.http
      .post<any>('api/timetable', ' ', { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  update(lessonTemplate: ILessonTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lessonTemplate);
    return this.http
      .put<ILessonTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILessonTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILessonTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lessonTemplate: ILessonTemplate): ILessonTemplate {
    const copy: ILessonTemplate = Object.assign({}, lessonTemplate, {
      repeatStartDate:
        lessonTemplate.repeatStartDate != null && lessonTemplate.repeatStartDate.isValid()
          ? lessonTemplate.repeatStartDate.format(DATE_FORMAT)
          : null,
      repeatUntilDate:
        lessonTemplate.repeatUntilDate != null && lessonTemplate.repeatUntilDate.isValid()
          ? lessonTemplate.repeatUntilDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.repeatStartDate = res.body.repeatStartDate != null ? moment(res.body.repeatStartDate) : null;
      res.body.repeatUntilDate = res.body.repeatUntilDate != null ? moment(res.body.repeatUntilDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lessonTemplate: ILessonTemplate) => {
        lessonTemplate.repeatStartDate = lessonTemplate.repeatStartDate != null ? moment(lessonTemplate.repeatStartDate) : null;
        lessonTemplate.repeatUntilDate = lessonTemplate.repeatUntilDate != null ? moment(lessonTemplate.repeatUntilDate) : null;
      });
    }
    return res;
  }
}
