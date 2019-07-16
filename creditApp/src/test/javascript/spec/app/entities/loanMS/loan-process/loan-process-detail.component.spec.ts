/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CreditAppTestModule } from '../../../../test.module';
import { LoanProcessDetailComponent } from 'app/entities/loanMS/loan-process/loan-process-detail.component';
import { LoanProcess } from 'app/shared/model/loanMS/loan-process.model';

describe('Component Tests', () => {
  describe('LoanProcess Management Detail Component', () => {
    let comp: LoanProcessDetailComponent;
    let fixture: ComponentFixture<LoanProcessDetailComponent>;
    const route = ({ data: of({ loanProcess: new LoanProcess(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CreditAppTestModule],
        declarations: [LoanProcessDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LoanProcessDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LoanProcessDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.loanProcess).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
