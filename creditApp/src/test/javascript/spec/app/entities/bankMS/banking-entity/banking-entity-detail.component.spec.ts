/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingEntityDetailComponent } from 'app/entities/bankMS/banking-entity/banking-entity-detail.component';
import { BankingEntity } from 'app/shared/model/bankMS/banking-entity.model';

describe('Component Tests', () => {
  describe('BankingEntity Management Detail Component', () => {
    let comp: BankingEntityDetailComponent;
    let fixture: ComponentFixture<BankingEntityDetailComponent>;
    const route = ({ data: of({ bankingEntity: new BankingEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BankingEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankingEntityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bankingEntity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
