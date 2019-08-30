import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';

type EntityResponseType = HttpResponse<IBankingEntity>;
type EntityArrayResponseType = HttpResponse<IBankingEntity[]>;

@Injectable({ providedIn: 'root' })
export class BankingEntityService {
  public resourceUrl = SERVER_API_URL + 'services/bankms/api/banking-entities';

  constructor(protected http: HttpClient) {}

  create(bankingEntity: IBankingEntity): Observable<EntityResponseType> {
    return this.http.post<IBankingEntity>(this.resourceUrl, bankingEntity, { observe: 'response' });
  }

  update(bankingEntity: IBankingEntity): Observable<EntityResponseType> {
    return this.http.put<IBankingEntity>(this.resourceUrl, bankingEntity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankingEntity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  
  findByMnemonic(mnemonic: string): Observable<EntityResponseType> {
    return this.http.get<IBankingEntity>(`${this.resourceUrl}/mnemonic/${mnemonic}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankingEntity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
