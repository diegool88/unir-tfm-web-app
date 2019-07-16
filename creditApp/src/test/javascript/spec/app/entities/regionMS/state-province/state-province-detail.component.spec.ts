/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { StateProvinceDetailComponent } from 'app/entities/regionMS/state-province/state-province-detail.component';
import { StateProvince } from 'app/shared/model/regionMS/state-province.model';

describe('Component Tests', () => {
  describe('StateProvince Management Detail Component', () => {
    let comp: StateProvinceDetailComponent;
    let fixture: ComponentFixture<StateProvinceDetailComponent>;
    const route = ({ data: of({ stateProvince: new StateProvince(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [StateProvinceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StateProvinceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvinceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stateProvince).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
