/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AmortizationTableService } from 'app/entities/loanMS/amortization-table/amortization-table.service';
import { IAmortizationTable, AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

describe('Service Tests', () => {
  describe('AmortizationTable Service', () => {
    let injector: TestBed;
    let service: AmortizationTableService;
    let httpMock: HttpTestingController;
    let elemDefault: IAmortizationTable;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AmortizationTableService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AmortizationTable(0, 0, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dueDate: currentDate.format(DATE_FORMAT)
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

      it('should create a AmortizationTable', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dueDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dueDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new AmortizationTable(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AmortizationTable', async () => {
        const returnedFromService = Object.assign(
          {
            order: 1,
            dueDate: currentDate.format(DATE_FORMAT),
            amount: 1,
            interest: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate
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

      it('should return a list of AmortizationTable', async () => {
        const returnedFromService = Object.assign(
          {
            order: 1,
            dueDate: currentDate.format(DATE_FORMAT),
            amount: 1,
            interest: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dueDate: currentDate
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

      it('should delete a AmortizationTable', async () => {
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
