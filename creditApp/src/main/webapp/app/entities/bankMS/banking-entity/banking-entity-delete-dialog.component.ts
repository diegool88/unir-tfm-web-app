import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { BankingEntityService } from './banking-entity.service';

@Component({
  selector: 'jhi-banking-entity-delete-dialog',
  templateUrl: './banking-entity-delete-dialog.component.html'
})
export class BankingEntityDeleteDialogComponent {
  bankingEntity: IBankingEntity;

  constructor(
    protected bankingEntityService: BankingEntityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bankingEntityService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bankingEntityListModification',
        content: 'Deleted an bankingEntity'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-banking-entity-delete-popup',
  template: ''
})
export class BankingEntityDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingEntity }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BankingEntityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bankingEntity = bankingEntity;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/banking-entity', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/banking-entity', { outlets: { popup: null } }]);
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
