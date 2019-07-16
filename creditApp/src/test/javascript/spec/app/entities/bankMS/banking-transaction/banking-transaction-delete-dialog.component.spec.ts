/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingTransactionDeleteDialogComponent } from 'app/entities/bankMS/banking-transaction/banking-transaction-delete-dialog.component';
import { BankingTransactionService } from 'app/entities/bankMS/banking-transaction/banking-transaction.service';

describe('Component Tests', () => {
  describe('BankingTransaction Management Delete Component', () => {
    let comp: BankingTransactionDeleteDialogComponent;
    let fixture: ComponentFixture<BankingTransactionDeleteDialogComponent>;
    let service: BankingTransactionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingTransactionDeleteDialogComponent]
      })
        .overrideTemplate(BankingTransactionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankingTransactionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingTransactionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
