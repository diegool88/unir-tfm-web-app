import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { ILoanProcessWrapper } from 'app/shared/model/loanMS/loan-process-wrapper.model';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

type EntityResponseType = HttpResponse<ILoanProcess>;
type EntityArrayResponseType = HttpResponse<ILoanProcess[]>;
type RulesResultType = HttpResponse<boolean>;
type WrapperEntityResponseType = HttpResponse<ILoanProcessWrapper>;

@Injectable({ providedIn: 'root' })
export class LoanProcessService {
  public resourceUrl = SERVER_API_URL + 'services/loanms/api/loan-processes';
  public rulesResourceUrl = SERVER_API_URL + 'services/rulesenginems/api/business-rules/loan-process';

  constructor(protected http: HttpClient) {}

  create(loanProcess: ILoanProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanProcess);
    return this.http
      .post<ILoanProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  
  createComplete(loanProcessWrapper: ILoanProcessWrapper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanProcessWrapper);
    return this.http
      .post<ILoanProcess>(`${this.resourceUrl}-complete`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(loanProcess: ILoanProcess): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(loanProcess);
    return this.http
      .put<ILoanProcess>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILoanProcess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }
  
  findComplete(id: number): Observable<WrapperEntityResponseType> {
    return this.http
      .get<ILoanProcessWrapper>(`${this.resourceUrl}-complete/${id}`, { observe: 'response' })
      .pipe(map((res: WrapperEntityResponseType) => this.convertDateWrapperFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILoanProcess[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }
  
  processBusinessRules(customerBirthDate: any, customerMonthlyIncome: number, customerGenre: string, loanRequestedAmmount: number, loanPeriod: number): Observable<RulesResultType> {
    return this.http
      .get<boolean>(`${this.rulesResourceUrl}/${customerBirthDate}/${customerMonthlyIncome}/${customerGenre}/${loanRequestedAmmount}/${loanPeriod}`, { observe: 'response' })
      .pipe(map((res: RulesResultType) => res));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(loanProcess: ILoanProcess): ILoanProcess {
    const copy: ILoanProcess = Object.assign({}, loanProcess, {
      startDate: loanProcess.startDate != null && loanProcess.startDate.isValid() ? loanProcess.startDate.format(DATE_FORMAT) : null,
      endDate: loanProcess.endDate != null && loanProcess.endDate.isValid() ? loanProcess.endDate.format(DATE_FORMAT) : null
    });
    return copy;
  }
  
  protected convertDateWrapperFromClient(loanProcessWrapper: ILoanProcessWrapper): ILoanProcessWrapper {
    loanProcessWrapper.loanProcess = Object.assign({}, loanProcessWrapper.loanProcess, {
      startDate: loanProcessWrapper.loanProcess.startDate != null && loanProcessWrapper.loanProcess.startDate.isValid() ? loanProcessWrapper.loanProcess.startDate.format(DATE_FORMAT) : null,
      endDate: loanProcessWrapper.loanProcess.endDate != null && loanProcessWrapper.loanProcess.endDate.isValid() ? loanProcessWrapper.loanProcess.endDate.format(DATE_FORMAT) : null
    });
    loanProcessWrapper.amortizationSchedule.forEach((amortizationEntry: IAmortizationTable) => {
        amortizationEntry = Object.assign({}, amortizationEntry, { dueDate: amortizationEntry.dueDate != null && amortizationEntry.dueDate.isValid() ? amortizationEntry.dueDate.format(DATE_FORMAT) : null });
    });
    return loanProcessWrapper;
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
      res.body.forEach((loanProcess: ILoanProcess) => {
        loanProcess.startDate = loanProcess.startDate != null ? moment(loanProcess.startDate) : null;
        loanProcess.endDate = loanProcess.endDate != null ? moment(loanProcess.endDate) : null;
      });
    }
    return res;
  }
  
  protected convertDateWrapperFromServer(res: WrapperEntityResponseType): WrapperEntityResponseType {
    if (res.body) {
        res.body.loanProcess.startDate = res.body.loanProcess.startDate != null ? moment(res.body.loanProcess.startDate) : null;
        res.body.loanProcess.endDate = res.body.loanProcess.endDate != null ? moment(res.body.loanProcess.endDate) : null;
        res.body.amortizationSchedule.forEach((amortizationEntry: IAmortizationTable) => {
            amortizationEntry.dueDate = amortizationEntry.dueDate != null ? moment(amortizationEntry.dueDate) : null;
        });
    }
    return res;
  }
}
