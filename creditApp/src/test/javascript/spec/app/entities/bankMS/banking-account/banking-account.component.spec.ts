/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingAccountComponent } from 'app/entities/bankMS/banking-account/banking-account.component';
import { BankingAccountService } from 'app/entities/bankMS/banking-account/banking-account.service';
import { BankingAccount } from 'app/shared/model/bankMS/banking-account.model';

describe('Component Tests', () => {
  describe('BankingAccount Management Component', () => {
    let comp: BankingAccountComponent;
    let fixture: ComponentFixture<BankingAccountComponent>;
    let service: BankingAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingAccountComponent],
        providers: []
      })
        .overrideTemplate(BankingAccountComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BankingAccountComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingAccountService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BankingAccount(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bankingAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
