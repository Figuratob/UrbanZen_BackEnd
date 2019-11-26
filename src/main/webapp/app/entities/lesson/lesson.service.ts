import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILesson } from 'app/shared/model/lesson.model';

type EntityResponseType = HttpResponse<ILesson>;
type EntityArrayResponseType = HttpResponse<ILesson[]>;

@Injectable({ providedIn: 'root' })
export class LessonService {
  public resourceUrl = SERVER_API_URL + 'api/lessons';

  constructor(protected http: HttpClient) {}

  create(lesson: ILesson): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lesson);
    return this.http
      .post<ILesson>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lesson: ILesson): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lesson);
    return this.http
      .put<ILesson>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILesson>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILesson[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lesson: ILesson): ILesson {
    const copy: ILesson = Object.assign({}, lesson, {
      startDate: lesson.startDate != null && lesson.startDate.isValid() ? lesson.startDate.toJSON() : null,
      endDate: lesson.endDate != null && lesson.endDate.isValid() ? lesson.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lesson: ILesson) => {
        lesson.startDate = lesson.startDate != null ? moment(lesson.startDate) : null;
        lesson.endDate = lesson.endDate != null ? moment(lesson.endDate) : null;
      });
    }
    return res;
  }
}
