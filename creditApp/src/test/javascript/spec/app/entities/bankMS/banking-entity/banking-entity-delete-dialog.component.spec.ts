/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CreditAppTestModule } from '../../../../test.module';
import { BankingEntityDeleteDialogComponent } from 'app/entities/bankMS/banking-entity/banking-entity-delete-dialog.component';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity/banking-entity.service';

describe('Component Tests', () => {
  describe('BankingEntity Management Delete Component', () => {
    let comp: BankingEntityDeleteDialogComponent;
    let fixture: ComponentFixture<BankingEntityDeleteDialogComponent>;
    let service: BankingEntityService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [BankingEntityDeleteDialogComponent]
      })
        .overrideTemplate(BankingEntityDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BankingEntityDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BankingEntityService);
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
