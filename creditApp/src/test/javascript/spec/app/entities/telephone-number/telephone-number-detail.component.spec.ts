/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../test.module';
import { TelephoneNumberDetailComponent } from 'app/entities/telephone-number/telephone-number-detail.component';
import { TelephoneNumber } from 'app/shared/model/telephone-number.model';

describe('Component Tests', () => {
  describe('TelephoneNumber Management Detail Component', () => {
    let comp: TelephoneNumberDetailComponent;
    let fixture: ComponentFixture<TelephoneNumberDetailComponent>;
    const route = ({ data: of({ telephoneNumber: new TelephoneNumber(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [TelephoneNumberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TelephoneNumberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TelephoneNumberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.telephoneNumber).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
