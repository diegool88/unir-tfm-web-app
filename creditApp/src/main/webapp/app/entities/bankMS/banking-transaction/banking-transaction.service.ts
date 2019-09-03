import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

type EntityResponseType = HttpResponse<IBankingTransaction>;
type EntityArrayResponseType = HttpResponse<IBankingTransaction[]>;

@Injectable({ providedIn: 'root' })
export class BankingTransactionService {
  public resourceUrl = SERVER_API_URL + 'services/bankms/api/banking-transactions';

  constructor(protected http: HttpClient) {}

  create(bankingTransaction: IBankingTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankingTransaction);
    return this.http
      .post<IBankingTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  
  createWithTransfer(bankingTransaction: IBankingTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankingTransaction);
    return this.http
      .post<IBankingTransaction>(`${this.resourceUrl}-transfer`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bankingTransaction: IBankingTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bankingTransaction);
    return this.http
      .put<IBankingTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBankingTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBankingTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bankingTransaction: IBankingTransaction): IBankingTransaction {
    const copy: IBankingTransaction = Object.assign({}, bankingTransaction, {
      date: bankingTransaction.date != null && bankingTransaction.date.isValid() ? bankingTransaction.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bankingTransaction: IBankingTransaction) => {
        bankingTransaction.date = bankingTransaction.date != null ? moment(bankingTransaction.date) : null;
      });
    }
    return res;
  }
}
