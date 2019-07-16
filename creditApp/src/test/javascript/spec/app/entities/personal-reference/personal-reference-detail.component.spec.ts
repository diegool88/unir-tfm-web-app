/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../test.module';
import { PersonalReferenceDetailComponent } from 'app/entities/personal-reference/personal-reference-detail.component';
import { PersonalReference } from 'app/shared/model/personal-reference.model';

describe('Component Tests', () => {
  describe('PersonalReference Management Detail Component', () => {
    let comp: PersonalReferenceDetailComponent;
    let fixture: ComponentFixture<PersonalReferenceDetailComponent>;
    const route = ({ data: of({ personalReference: new PersonalReference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [PersonalReferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonalReferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonalReferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personalReference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
