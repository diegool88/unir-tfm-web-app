/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingEntityUpdateComponent } from 'app/entities/bankMS/banking-entity/banking-entity-update.component';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity/banking-entity.service';
import { BankingEntity } from 'app/shared/model/bankMS/banking-entity.model';

describe('Component Tests', () => {
  describe('BankingEntity Management Update Component', () => {
    let comp: BankingEntityUpdateComponent;
    let fixture: ComponentFixture<BankingEntityUpdateComponent>;
    let service: BankingEntityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingEntityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BankingEntityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankingEntityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingEntityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BankingEntity(123);
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
        const entity = new BankingEntity();
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
