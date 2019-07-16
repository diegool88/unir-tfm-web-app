import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { BankingAccountService } from './banking-account.service';

@Component({
  selector: 'jhi-banking-account-delete-dialog',
  templateUrl: './banking-account-delete-dialog.component.html'
})
export class BankingAccountDeleteDialogComponent {
  bankingAccount: IBankingAccount;

  constructor(
    protected bankingAccountService: BankingAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bankingAccountService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bankingAccountListModification',
        content: 'Deleted an bankingAccount'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-banking-account-delete-popup',
  template: ''
})
export class BankingAccountDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingAccount }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BankingAccountDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bankingAccount = bankingAccount;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/banking-account', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/banking-account', { outlets: { popup: null } }]);
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
