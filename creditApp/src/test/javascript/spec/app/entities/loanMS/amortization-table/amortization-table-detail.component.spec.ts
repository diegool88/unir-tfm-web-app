/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { AmortizationTableDetailComponent } from 'app/entities/loanMS/amortization-table/amortization-table-detail.component';
import { AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

describe('Component Tests', () => {
  describe('AmortizationTable Management Detail Component', () => {
    let comp: AmortizationTableDetailComponent;
    let fixture: ComponentFixture<AmortizationTableDetailComponent>;
    const route = ({ data: of({ amortizationTable: new AmortizationTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [AmortizationTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AmortizationTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.amortizationTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
