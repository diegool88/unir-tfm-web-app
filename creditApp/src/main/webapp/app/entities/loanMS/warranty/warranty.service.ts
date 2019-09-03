import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';

type EntityResponseType = HttpResponse<IWarranty>;
type EntityArrayResponseType = HttpResponse<IWarranty[]>;

@Injectable({ providedIn: 'root' })
export class WarrantyService {
  public resourceUrl = SERVER_API_URL + 'services/loanms/api/warranties';

  constructor(protected http: HttpClient) {}

  create(warranty: IWarranty): Observable<EntityResponseType> {
    return this.http.post<IWarranty>(this.resourceUrl, warranty, { observe: 'response' });
  }
  
  createMasive(warranties: IWarranty[]): Observable<EntityArrayResponseType> {
    return this.http.post<IWarranty[]>(`${this.resourceUrl}-masive`, { warranties: warranties }, { observe: 'response' });
  }

  update(warranty: IWarranty): Observable<EntityResponseType> {
    return this.http.put<IWarranty>(this.resourceUrl, warranty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWarranty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWarranty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
