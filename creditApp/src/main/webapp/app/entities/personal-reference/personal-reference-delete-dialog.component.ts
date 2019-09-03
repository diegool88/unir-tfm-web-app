import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonalReference } from 'app/shared/model/personal-reference.model';
import { PersonalReferenceService } from './personal-reference.service';

@Component({
  selector: 'jhi-personal-reference-delete-dialog',
  templateUrl: './personal-reference-delete-dialog.component.html'
})
export class PersonalReferenceDeleteDialogComponent {
  personalReference: IPersonalReference;

  constructor(
    protected personalReferenceService: PersonalReferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personalReferenceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personalReferenceListModification',
        content: 'Deleted an personalReference'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-personal-reference-delete-popup',
  template: ''
})
export class PersonalReferenceDeletePopupComponent implements OnInit, OnDestroy {
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
    this.activatedRoute.data.subscribe(({ personalReference }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonalReferenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.personalReference = personalReference;
        this.ngbModalRef.result.then(
          result => {
            if (this.mode !== 'wizard') this.router.navigate(['/personal-reference', { outlets: { popup: null } }]);
            else this.router.navigate(['/wizard-main/personal-reference', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            if (this.mode !== 'wizard') this.router.navigate(['/personal-reference', { outlets: { popup: null } }]);
            else this.router.navigate(['/wizard-main/personal-reference', { outlets: { popup: null } }]);
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
