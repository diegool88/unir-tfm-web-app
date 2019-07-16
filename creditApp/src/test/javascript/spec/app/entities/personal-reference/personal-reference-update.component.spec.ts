/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CreditAppTestModule } from '../../../test.module';
import { PersonalReferenceUpdateComponent } from 'app/entities/personal-reference/personal-reference-update.component';
import { PersonalReferenceService } from 'app/entities/personal-reference/personal-reference.service';
import { PersonalReference } from 'app/shared/model/personal-reference.model';

describe('Component Tests', () => {
  describe('PersonalReference Management Update Component', () => {
    let comp: PersonalReferenceUpdateComponent;
    let fixture: ComponentFixture<PersonalReferenceUpdateComponent>;
    let service: PersonalReferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [PersonalReferenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonalReferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonalReferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonalReferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonalReference(123);
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
        const entity = new PersonalReference();
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
