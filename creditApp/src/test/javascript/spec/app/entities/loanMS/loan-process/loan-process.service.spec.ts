/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LoanProcessService } from 'app/entities/loanMS/loan-process/loan-process.service';
import { ILoanProcess, LoanProcess, LoanProcessStatus } from 'app/shared/model/loanMS/loan-process.model';

describe('Service Tests', () => {
  describe('LoanProcess Service', () => {
    let injector: TestBed;
    let service: LoanProcessService;
    let httpMock: HttpTestingController;
    let elemDefault: ILoanProcess;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LoanProcessService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LoanProcess(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        LoanProcessStatus.APPROVED
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
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

      it('should create a LoanProcess', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new LoanProcess(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LoanProcess', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            requestedAmount: 1,
            givenAmount: 1,
            loanPeriod: 1,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            justification: 'BBBBBB',
            debtorIdentification: 'BBBBBB',
            debtorIdentificationType: 'BBBBBB',
            debtorCountry: 'BBBBBB',
            bankingEntityMnemonic: 'BBBBBB',
            bankingProductMnemonic: 'BBBBBB',
            rulesEngineResult: true,
            loanProcessStatus: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
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

      it('should return a list of LoanProcess', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            requestedAmount: 1,
            givenAmount: 1,
            loanPeriod: 1,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            justification: 'BBBBBB',
            debtorIdentification: 'BBBBBB',
            debtorIdentificationType: 'BBBBBB',
            debtorCountry: 'BBBBBB',
            bankingEntityMnemonic: 'BBBBBB',
            bankingProductMnemonic: 'BBBBBB',
            rulesEngineResult: true,
            loanProcessStatus: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
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

      it('should delete a LoanProcess', async () => {
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
