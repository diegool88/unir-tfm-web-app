import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

type EntityResponseType = HttpResponse<IAmortizationTable>;
type EntityArrayResponseType = HttpResponse<IAmortizationTable[]>;

@Injectable({ providedIn: 'root' })
export class AmortizationTableService {
  public resourceUrl = SERVER_API_URL + 'services/loanms/api/amortization-tables';

  constructor(protected http: HttpClient) {}

  create(amortizationTable: IAmortizationTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationTable);
    return this.http
      .post<IAmortizationTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(amortizationTable: IAmortizationTable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(amortizationTable);
    return this.http
      .put<IAmortizationTable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAmortizationTable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAmortizationTable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  
  calculate(requestedAmount: number, annualInterestRate: number, startDate: any, loanPeriod: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IAmortizationTable[]>(`${this.resourceUrl}/simulate/${requestedAmount}/${annualInterestRate}/${startDate}/${loanPeriod}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(amortizationTable: IAmortizationTable): IAmortizationTable {
    const copy: IAmortizationTable = Object.assign({}, amortizationTable, {
      dueDate:
        amortizationTable.dueDate != null && amortizationTable.dueDate.isValid() ? amortizationTable.dueDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dueDate = res.body.dueDate != null ? moment(res.body.dueDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((amortizationTable: IAmortizationTable) => {
        amortizationTable.dueDate = amortizationTable.dueDate != null ? moment(amortizationTable.dueDate) : null;
      });
    }
    return res;
  }
}
