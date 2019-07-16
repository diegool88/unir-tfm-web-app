/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingTransactionUpdateComponent } from 'app/entities/bankMS/banking-transaction/banking-transaction-update.component';
import { BankingTransactionService } from 'app/entities/bankMS/banking-transaction/banking-transaction.service';
import { BankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

describe('Component Tests', () => {
  describe('BankingTransaction Management Update Component', () => {
    let comp: BankingTransactionUpdateComponent;
    let fixture: ComponentFixture<BankingTransactionUpdateComponent>;
    let service: BankingTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BankingTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankingTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BankingTransaction(123);
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
        const entity = new BankingTransaction();
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
