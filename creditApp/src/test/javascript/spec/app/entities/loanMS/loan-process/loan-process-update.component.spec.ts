/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { LoanProcessUpdateComponent } from 'app/entities/loanMS/loan-process/loan-process-update.component';
import { LoanProcessService } from 'app/entities/loanMS/loan-process/loan-process.service';
import { LoanProcess } from 'app/shared/model/loanMS/loan-process.model';

describe('Component Tests', () => {
  describe('LoanProcess Management Update Component', () => {
    let comp: LoanProcessUpdateComponent;
    let fixture: ComponentFixture<LoanProcessUpdateComponent>;
    let service: LoanProcessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [LoanProcessUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LoanProcessUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanProcessUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanProcessService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoanProcess(123);
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
        const entity = new LoanProcess();
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
