/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PersonalReferenceService } from 'app/entities/personal-reference/personal-reference.service';
import { IPersonalReference, PersonalReference, Genre, Relation } from 'app/shared/model/personal-reference.model';

describe('Service Tests', () => {
  describe('PersonalReference Service', () => {
    let injector: TestBed;
    let service: PersonalReferenceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonalReference;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PersonalReferenceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PersonalReference(0, 'AAAAAAA', 'AAAAAAA', Genre.MALE, 'AAAAAAA', currentDate, Relation.FRIEND);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthDate: currentDate.format(DATE_FORMAT)
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

      it('should create a PersonalReference', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new PersonalReference(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PersonalReference', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            lastname: 'BBBBBB',
            genre: 'BBBBBB',
            email: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            relation: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthDate: currentDate
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

      it('should return a list of PersonalReference', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            lastname: 'BBBBBB',
            genre: 'BBBBBB',
            email: 'BBBBBB',
            birthDate: currentDate.format(DATE_FORMAT),
            relation: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthDate: currentDate
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

      it('should delete a PersonalReference', async () => {
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
