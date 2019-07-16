/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../test.module';
import { TelephoneNumberUpdateComponent } from 'app/entities/telephone-number/telephone-number-update.component';
import { TelephoneNumberService } from 'app/entities/telephone-number/telephone-number.service';
import { TelephoneNumber } from 'app/shared/model/telephone-number.model';

describe('Component Tests', () => {
  describe('TelephoneNumber Management Update Component', () => {
    let comp: TelephoneNumberUpdateComponent;
    let fixture: ComponentFixture<TelephoneNumberUpdateComponent>;
    let service: TelephoneNumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [TelephoneNumberUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TelephoneNumberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TelephoneNumberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TelephoneNumberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TelephoneNumber(123);
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
        const entity = new TelephoneNumber();
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
