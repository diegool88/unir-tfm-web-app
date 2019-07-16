/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingTransactionComponent } from 'app/entities/bankMS/banking-transaction/banking-transaction.component';
import { BankingTransactionService } from 'app/entities/bankMS/banking-transaction/banking-transaction.service';
import { BankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

describe('Component Tests', () => {
  describe('BankingTransaction Management Component', () => {
    let comp: BankingTransactionComponent;
    let fixture: ComponentFixture<BankingTransactionComponent>;
    let service: BankingTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingTransactionComponent],
        providers: []
      })
        .overrideTemplate(BankingTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankingTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BankingTransaction(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bankingTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
