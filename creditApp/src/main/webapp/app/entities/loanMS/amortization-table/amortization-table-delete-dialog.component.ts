import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { AmortizationTableService } from './amortization-table.service';

@Component({
  selector: 'jhi-amortization-table-delete-dialog',
  templateUrl: './amortization-table-delete-dialog.component.html'
})
export class AmortizationTableDeleteDialogComponent {
  amortizationTable: IAmortizationTable;

  constructor(
    protected amortizationTableService: AmortizationTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.amortizationTableService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'amortizationTableListModification',
        content: 'Deleted an amortizationTable'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-amortization-table-delete-popup',
  template: ''
})
export class AmortizationTableDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationTable }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AmortizationTableDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.amortizationTable = amortizationTable;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/amortization-table', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/amortization-table', { outlets: { popup: null } }]);
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
