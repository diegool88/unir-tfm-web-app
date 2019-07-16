import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { WarrantyService } from './warranty.service';

@Component({
  selector: 'jhi-warranty-delete-dialog',
  templateUrl: './warranty-delete-dialog.component.html'
})
export class WarrantyDeleteDialogComponent {
  warranty: IWarranty;

  constructor(protected warrantyService: WarrantyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.warrantyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'warrantyListModification',
        content: 'Deleted an warranty'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-warranty-delete-popup',
  template: ''
})
export class WarrantyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ warranty }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WarrantyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.warranty = warranty;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/warranty', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/warranty', { outlets: { popup: null } }]);
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
