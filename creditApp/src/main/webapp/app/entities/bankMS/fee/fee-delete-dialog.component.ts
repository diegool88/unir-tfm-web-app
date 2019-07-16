import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFee } from 'app/shared/model/bankMS/fee.model';
import { FeeService } from './fee.service';

@Component({
  selector: 'jhi-fee-delete-dialog',
  templateUrl: './fee-delete-dialog.component.html'
})
export class FeeDeleteDialogComponent {
  fee: IFee;

  constructor(protected feeService: FeeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.feeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'feeListModification',
        content: 'Deleted an fee'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fee-delete-popup',
  template: ''
})
export class FeeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fee }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FeeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fee = fee;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fee', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fee', { outlets: { popup: null } }]);
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
