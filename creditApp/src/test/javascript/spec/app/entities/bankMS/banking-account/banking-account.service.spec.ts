/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { BankingAccountService } from 'app/entities/bankMS/banking-account/banking-account.service';
import { IBankingAccount, BankingAccount, AccountType } from 'app/shared/model/bankMS/banking-account.model';

describe('Service Tests', () => {
  describe('BankingAccount Service', () => {
    let injector: TestBed;
    let service: BankingAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IBankingAccount;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BankingAccountService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new BankingAccount(0, 0, AccountType.SAVINGS, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a BankingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new BankingAccount(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a BankingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            accountType: 'BBBBBB',
            currentBalance: 1,
            availableBalance: 1,
            customerIdentification: 'BBBBBB',
            customerIdentificationType: 'BBBBBB',
            customerCountry: 'BBBBBB',
            bankingEntityMnemonic: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of BankingAccount', async () => {
        const returnedFromService = Object.assign(
          {
            number: 1,
            accountType: 'BBBBBB',
            currentBalance: 1,
            availableBalance: 1,
            customerIdentification: 'BBBBBB',
            customerIdentificationType: 'BBBBBB',
            customerCountry: 'BBBBBB',
            bankingEntityMnemonic: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a BankingAccount', async () => {
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
