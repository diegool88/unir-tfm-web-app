/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CreditAppTestModule } from '../../../../test.module';
import { AmortizationTableDeleteDialogComponent } from 'app/entities/loanMS/amortization-table/amortization-table-delete-dialog.component';
import { AmortizationTableService } from 'app/entities/loanMS/amortization-table/amortization-table.service';

describe('Component Tests', () => {
  describe('AmortizationTable Management Delete Component', () => {
    let comp: AmortizationTableDeleteDialogComponent;
    let fixture: ComponentFixture<AmortizationTableDeleteDialogComponent>;
    let service: AmortizationTableService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [AmortizationTableDeleteDialogComponent]
      })
        .overrideTemplate(AmortizationTableDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmortizationTableDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AmortizationTableService);
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
