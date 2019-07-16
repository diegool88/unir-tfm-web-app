/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { AmortizationTableUpdateComponent } from 'app/entities/loanMS/amortization-table/amortization-table-update.component';
import { AmortizationTableService } from 'app/entities/loanMS/amortization-table/amortization-table.service';
import { AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

describe('Component Tests', () => {
  describe('AmortizationTable Management Update Component', () => {
    let comp: AmortizationTableUpdateComponent;
    let fixture: ComponentFixture<AmortizationTableUpdateComponent>;
    let service: AmortizationTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [AmortizationTableUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AmortizationTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmortizationTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationTable(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AmortizationTable();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
