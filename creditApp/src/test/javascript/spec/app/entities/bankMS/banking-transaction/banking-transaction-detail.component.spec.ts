/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingTransactionDetailComponent } from 'app/entities/bankMS/banking-transaction/banking-transaction-detail.component';
import { BankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

describe('Component Tests', () => {
  describe('BankingTransaction Management Detail Component', () => {
    let comp: BankingTransactionDetailComponent;
    let fixture: ComponentFixture<BankingTransactionDetailComponent>;
    const route = ({ data: of({ bankingTransaction: new BankingTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BankingTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankingTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bankingTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
