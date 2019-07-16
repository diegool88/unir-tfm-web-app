/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CreditAppTestModule } from '../../../../test.module';
import { WarrantyComponent } from 'app/entities/loanMS/warranty/warranty.component';
import { WarrantyService } from 'app/entities/loanMS/warranty/warranty.service';
import { Warranty } from 'app/shared/model/loanMS/warranty.model';

describe('Component Tests', () => {
  describe('Warranty Management Component', () => {
    let comp: WarrantyComponent;
    let fixture: ComponentFixture<WarrantyComponent>;
    let service: WarrantyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [WarrantyComponent],
        providers: []
      })
        .overrideTemplate(WarrantyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WarrantyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WarrantyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Warranty(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.warranties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
