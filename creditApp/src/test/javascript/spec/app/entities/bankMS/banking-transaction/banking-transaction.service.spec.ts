/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BankingTransactionService } from 'app/entities/bankMS/banking-transaction/banking-transaction.service';
import { IBankingTransaction, BankingTransaction, TransactionType, AccountType } from 'app/shared/model/bankMS/banking-transaction.model';

describe('Service Tests', () => {
  describe('BankingTransaction Service', () => {
    let injector: TestBed;
    let service: BankingTransactionService;
    let httpMock: HttpTestingController;
    let elemDefault: IBankingTransaction;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BankingTransactionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BankingTransaction(
        0,
        0,
        currentDate,
        0,
        TransactionType.DEPOSIT,
        0,
        AccountType.SAVINGS,
        'AAAAAAA',
        0,
        AccountType.SAVINGS,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a BankingTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .create(new BankingTransaction(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a BankingTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            date: currentDate.format(DATE_TIME_FORMAT),
            ammount: 1,
            transactionType: 'BBBBBB',
            extOriginAccount: 1,
            extOriginAccountType: 'BBBBBB',
            extOriginAccountBank: 'BBBBBB',
            extDestinationAccount: 1,
            extDestinationAccountType: 'BBBBBB',
            extDestinationAccountBank: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of BankingTransaction', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            date: currentDate.format(DATE_TIME_FORMAT),
            ammount: 1,
            transactionType: 'BBBBBB',
            extOriginAccount: 1,
            extOriginAccountType: 'BBBBBB',
            extOriginAccountBank: 'BBBBBB',
            extDestinationAccount: 1,
            extDestinationAccountType: 'BBBBBB',
            extDestinationAccountBank: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BankingTransaction', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
