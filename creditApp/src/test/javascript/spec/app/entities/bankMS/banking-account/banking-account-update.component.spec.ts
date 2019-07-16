/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingAccountUpdateComponent } from 'app/entities/bankMS/banking-account/banking-account-update.component';
import { BankingAccountService } from 'app/entities/bankMS/banking-account/banking-account.service';
import { BankingAccount } from 'app/shared/model/bankMS/banking-account.model';

describe('Component Tests', () => {
  describe('BankingAccount Management Update Component', () => {
    let comp: BankingAccountUpdateComponent;
    let fixture: ComponentFixture<BankingAccountUpdateComponent>;
    let service: BankingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingAccountUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BankingAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankingAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BankingAccount(123);
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
        const entity = new BankingAccount();
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
