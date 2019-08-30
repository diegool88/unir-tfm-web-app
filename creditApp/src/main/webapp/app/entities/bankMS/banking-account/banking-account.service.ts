import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';

type EntityResponseType = HttpResponse<IBankingAccount>;
type EntityArrayResponseType = HttpResponse<IBankingAccount[]>;

@Injectable({ providedIn: 'root' })
export class BankingAccountService {
  public resourceUrl = SERVER_API_URL + 'services/bankms/api/banking-accounts';

  constructor(protected http: HttpClient) {}

  create(bankingAccount: IBankingAccount): Observable<EntityResponseType> {
    return this.http.post<IBankingAccount>(this.resourceUrl, bankingAccount, { observe: 'response' });
  }

  update(bankingAccount: IBankingAccount): Observable<EntityResponseType> {
    return this.http.put<IBankingAccount>(this.resourceUrl, bankingAccount, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBankingAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  
  findByNumber(accountNumber: number, accountType: string, bankingEntityMnemonic: string): Observable<EntityResponseType> {
    return this.http.get<IBankingAccount>(`${this.resourceUrl}/number/${accountNumber}/${accountType}/${bankingEntityMnemonic}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankingAccount[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  
  queryByCustomer(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBankingAccount[]>(`${this.resourceUrl}/customer`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
