/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CreditAppTestModule } from '../../../../test.module';
import { StateProvinceDeleteDialogComponent } from 'app/entities/regionMS/state-province/state-province-delete-dialog.component';
import { StateProvinceService } from 'app/entities/regionMS/state-province/state-province.service';

describe('Component Tests', () => {
  describe('StateProvince Management Delete Component', () => {
    let comp: StateProvinceDeleteDialogComponent;
    let fixture: ComponentFixture<StateProvinceDeleteDialogComponent>;
    let service: StateProvinceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [StateProvinceDeleteDialogComponent]
      })
        .overrideTemplate(StateProvinceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StateProvinceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateProvinceService);
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