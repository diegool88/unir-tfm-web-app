import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from './loan-process.service';

@Component({
  selector: 'jhi-loan-process-delete-dialog',
  templateUrl: './loan-process-delete-dialog.component.html'
})
export class LoanProcessDeleteDialogComponent {
  loanProcess: ILoanProcess;

  constructor(
    protected loanProcessService: LoanProcessService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.loanProcessService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'loanProcessListModification',
        content: 'Deleted an loanProcess'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-loan-process-delete-popup',
  template: ''
})
export class LoanProcessDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanProcess }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LoanProcessDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.loanProcess = loanProcess;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/loan-process', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/loan-process', { outlets: { popup: null } }]);
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
