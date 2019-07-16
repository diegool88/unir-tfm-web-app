/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { StateProvinceUpdateComponent } from 'app/entities/regionMS/state-province/state-province-update.component';
import { StateProvinceService } from 'app/entities/regionMS/state-province/state-province.service';
import { StateProvince } from 'app/shared/model/regionMS/state-province.model';

describe('Component Tests', () => {
  describe('StateProvince Management Update Component', () => {
    let comp: StateProvinceUpdateComponent;
    let fixture: ComponentFixture<StateProvinceUpdateComponent>;
    let service: StateProvinceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [StateProvinceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StateProvinceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateProvinceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateProvinceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StateProvince(123);
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
        const entity = new StateProvince();
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
