import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITelephoneNumber } from 'app/shared/model/telephone-number.model';
import { TelephoneNumberService } from './telephone-number.service';

@Component({
  selector: 'jhi-telephone-number-delete-dialog',
  templateUrl: './telephone-number-delete-dialog.component.html'
})
export class TelephoneNumberDeleteDialogComponent {
  telephoneNumber: ITelephoneNumber;

  constructor(
    protected telephoneNumberService: TelephoneNumberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.telephoneNumberService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'telephoneNumberListModification',
        content: 'Deleted an telephoneNumber'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-telephone-number-delete-popup',
  template: ''
})
export class TelephoneNumberDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;
  mode: any;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    //Wizard
    this.activatedRoute.queryParams.subscribe(queryParams => {
      if (queryParams && queryParams.mode) {
        this.mode = queryParams.mode;
      }
    });
    this.activatedRoute.data.subscribe(({ telephoneNumber }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TelephoneNumberDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.telephoneNumber = telephoneNumber;
        this.ngbModalRef.result.then(
          result => {
            if (this.mode !== 'wizard') this.router.navigate(['/telephone-number', { outlets: { popup: null } }]);
            else this.router.navigate(['/wizard-main/telephone-number', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            if (this.mode !== 'wizard') this.router.navigate(['/telephone-number', { outlets: { popup: null } }]);
            else this.router.navigate(['/wizard-main/telephone-number', { outlets: { popup: null } }]);
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
