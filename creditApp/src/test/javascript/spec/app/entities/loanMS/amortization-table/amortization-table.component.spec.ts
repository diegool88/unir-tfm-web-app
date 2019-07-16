/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CreditAppTestModule } from '../../../../test.module';
import { AmortizationTableComponent } from 'app/entities/loanMS/amortization-table/amortization-table.component';
import { AmortizationTableService } from 'app/entities/loanMS/amortization-table/amortization-table.service';
import { AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

describe('Component Tests', () => {
  describe('AmortizationTable Management Component', () => {
    let comp: AmortizationTableComponent;
    let fixture: ComponentFixture<AmortizationTableComponent>;
    let service: AmortizationTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [AmortizationTableComponent],
        providers: []
      })
        .overrideTemplate(AmortizationTableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationTableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationTableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AmortizationTable(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.amortizationTables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
