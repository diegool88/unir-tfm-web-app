import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPersonalReference } from 'app/shared/model/personal-reference.model';

type EntityResponseType = HttpResponse<IPersonalReference>;
type EntityArrayResponseType = HttpResponse<IPersonalReference[]>;

@Injectable({ providedIn: 'root' })
export class PersonalReferenceService {
  public resourceUrl = SERVER_API_URL + 'api/personal-references';

  constructor(protected http: HttpClient) {}

  create(personalReference: IPersonalReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalReference);
    return this.http
      .post<IPersonalReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personalReference: IPersonalReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personalReference);
    return this.http
      .put<IPersonalReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonalReference>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonalReference[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(personalReference: IPersonalReference): IPersonalReference {
    const copy: IPersonalReference = Object.assign({}, personalReference, {
      birthDate:
        personalReference.birthDate != null && personalReference.birthDate.isValid()
          ? personalReference.birthDate.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personalReference: IPersonalReference) => {
        personalReference.birthDate = personalReference.birthDate != null ? moment(personalReference.birthDate) : null;
      });
    }
    return res;
  }
}
