import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStateProvince } from 'app/shared/model/regionMS/state-province.model';
import { StateProvinceService } from './state-province.service';

@Component({
  selector: 'jhi-state-province-delete-dialog',
  templateUrl: './state-province-delete-dialog.component.html'
})
export class StateProvinceDeleteDialogComponent {
  stateProvince: IStateProvince;

  constructor(
    protected stateProvinceService: StateProvinceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stateProvinceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stateProvinceListModification',
        content: 'Deleted an stateProvince'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-state-province-delete-popup',
  template: ''
})
export class StateProvinceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stateProvince }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StateProvinceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stateProvince = stateProvince;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/state-province', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/state-province', { outlets: { popup: null } }]);
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
