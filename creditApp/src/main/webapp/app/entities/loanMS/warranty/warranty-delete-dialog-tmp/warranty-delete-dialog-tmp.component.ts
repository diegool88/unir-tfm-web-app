import { Component, OnInit, OnDestroy } from '@angular/core';
import { WarrantyService } from "app/entities/loanMS/warranty";
import { NgbActiveModal, NgbModalRef, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { JhiEventManager } from "ng-jhipster";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { WizardService } from "app/layouts/wizard/wizard.service";
import { IWarranty } from "app/shared/model/loanMS/warranty.model";

@Component({
  selector: 'jhi-warranty-delete-dialog-tmp',
  templateUrl: './warranty-delete-dialog-tmp.component.html',
  styleUrls: ['./warranty-delete-dialog-tmp.component.scss']
})
export class WarrantyDeleteDialogTmpComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  warranties: IWarranty[];
  warranty: IWarranty;
  constructor(protected warrantyService: WarrantyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager, protected wizardService: WizardService) { }
  
  clear() {
    this.activeModal.dismiss('cancel');
  }
  
  ngOnInit() {
      this.warranties = this.wizardService.getWarranties();
  }
  
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

    confirmDelete(id: number) {
      this.warranties.splice(id, 1);
      this.wizardService.setWarranties(this.warranties);
      this.eventManager.broadcast({
        name: 'warrantyListModification',
        content: 'Deleted an warranty'
      });
      this.activeModal.dismiss(true);
    }

}

@Component({
  selector: 'jhi-warranty-delete-popup-tmp',
  template: ''
})
export class WarrantyDeletePopupTmpComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ warranty }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WarrantyDeleteDialogTmpComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.warranty = warranty;
        this.ngbModalRef.result.then(
            result => {
              this.router.navigate(['/wizard-main/warranty', { outlets: { popup: null } }], { queryParams: { mode: 'wizard'} });
              this.ngbModalRef = null;
            },
            reason => {
              this.router.navigate(['/wizard-main/warranty', { outlets: { popup: null } }], { queryParams: { mode: 'wizard'} });
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