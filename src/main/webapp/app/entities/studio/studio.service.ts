import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStudio } from 'app/shared/model/studio.model';

type EntityResponseType = HttpResponse<IStudio>;
type EntityArrayResponseType = HttpResponse<IStudio[]>;

@Injectable({ providedIn: 'root' })
export class StudioService {
  public resourceUrl = SERVER_API_URL + 'api/studios';

  constructor(protected http: HttpClient) {}

  create(studio: IStudio): Observable<EntityResponseType> {
    return this.http.post<IStudio>(this.resourceUrl, studio, { observe: 'response' });
  }

  update(studio: IStudio): Observable<EntityResponseType> {
    return this.http.put<IStudio>(this.resourceUrl, studio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStudio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStudio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
