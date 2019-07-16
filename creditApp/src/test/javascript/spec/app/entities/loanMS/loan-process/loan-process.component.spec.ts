/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CreditAppTestModule } from '../../../../test.module';
import { LoanProcessComponent } from 'app/entities/loanMS/loan-process/loan-process.component';
import { LoanProcessService } from 'app/entities/loanMS/loan-process/loan-process.service';
import { LoanProcess } from 'app/shared/model/loanMS/loan-process.model';

describe('Component Tests', () => {
  describe('LoanProcess Management Component', () => {
    let comp: LoanProcessComponent;
    let fixture: ComponentFixture<LoanProcessComponent>;
    let service: LoanProcessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [LoanProcessComponent],
        providers: []
      })
        .overrideTemplate(LoanProcessComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanProcessComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanProcessService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LoanProcess(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.loanProcesses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
