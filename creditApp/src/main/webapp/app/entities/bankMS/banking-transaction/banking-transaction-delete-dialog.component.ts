import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';
import { BankingTransactionService } from './banking-transaction.service';

@Component({
  selector: 'jhi-banking-transaction-delete-dialog',
  templateUrl: './banking-transaction-delete-dialog.component.html'
})
export class BankingTransactionDeleteDialogComponent {
  bankingTransaction: IBankingTransaction;

  constructor(
    protected bankingTransactionService: BankingTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bankingTransactionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bankingTransactionListModification',
        content: 'Deleted an bankingTransaction'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-banking-transaction-delete-popup',
  template: ''
})
export class BankingTransactionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingTransaction }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BankingTransactionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bankingTransaction = bankingTransaction;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/banking-transaction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/banking-transaction', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
