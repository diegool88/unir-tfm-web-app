/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CreditAppTestModule } from '../../../../test.module';
import { WarrantyDeleteDialogComponent } from 'app/entities/loanMS/warranty/warranty-delete-dialog.component';
import { WarrantyService } from 'app/entities/loanMS/warranty/warranty.service';

describe('Component Tests', () => {
  describe('Warranty Management Delete Component', () => {
    let comp: WarrantyDeleteDialogComponent;
    let fixture: ComponentFixture<WarrantyDeleteDialogComponent>;
    let service: WarrantyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [WarrantyDeleteDialogComponent]
      })
        .overrideTemplate(WarrantyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WarrantyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WarrantyService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
