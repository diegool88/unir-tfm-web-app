/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingAccountDetailComponent } from 'app/entities/bankMS/banking-account/banking-account-detail.component';
import { BankingAccount } from 'app/shared/model/bankMS/banking-account.model';

describe('Component Tests', () => {
  describe('BankingAccount Management Detail Component', () => {
    let comp: BankingAccountDetailComponent;
    let fixture: ComponentFixture<BankingAccountDetailComponent>;
    const route = ({ data: of({ bankingAccount: new BankingAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BankingAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankingAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bankingAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
