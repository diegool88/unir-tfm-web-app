import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITelephoneNumber } from 'app/shared/model/telephone-number.model';

type EntityResponseType = HttpResponse<ITelephoneNumber>;
type EntityArrayResponseType = HttpResponse<ITelephoneNumber[]>;

@Injectable({ providedIn: 'root' })
export class TelephoneNumberService {
  public resourceUrl = SERVER_API_URL + 'api/telephone-numbers';

  constructor(protected http: HttpClient) {}

  create(telephoneNumber: ITelephoneNumber): Observable<EntityResponseType> {
    return this.http.post<ITelephoneNumber>(this.resourceUrl, telephoneNumber, { observe: 'response' });
  }

  update(telephoneNumber: ITelephoneNumber): Observable<EntityResponseType> {
    return this.http.put<ITelephoneNumber>(this.resourceUrl, telephoneNumber, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITelephoneNumber>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITelephoneNumber[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
