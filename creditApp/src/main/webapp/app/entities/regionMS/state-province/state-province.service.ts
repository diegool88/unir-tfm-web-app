import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStateProvince } from 'app/shared/model/regionMS/state-province.model';

type EntityResponseType = HttpResponse<IStateProvince>;
type EntityArrayResponseType = HttpResponse<IStateProvince[]>;

@Injectable({ providedIn: 'root' })
export class StateProvinceService {
  public resourceUrl = SERVER_API_URL + 'services/regionms/api/state-provinces';

  constructor(protected http: HttpClient) {}

  create(stateProvince: IStateProvince): Observable<EntityResponseType> {
    return this.http.post<IStateProvince>(this.resourceUrl, stateProvince, { observe: 'response' });
  }

  update(stateProvince: IStateProvince): Observable<EntityResponseType> {
    return this.http.put<IStateProvince>(this.resourceUrl, stateProvince, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStateProvince>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStateProvince[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
