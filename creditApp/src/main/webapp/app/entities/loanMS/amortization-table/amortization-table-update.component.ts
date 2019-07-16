import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAmortizationTable, AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { AmortizationTableService } from './amortization-table.service';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from 'app/entities/loanMS/loan-process';

@Component({
  selector: 'jhi-amortization-table-update',
  templateUrl: './amortization-table-update.component.html'
})
export class AmortizationTableUpdateComponent implements OnInit {
  isSaving: boolean;

  loanprocesses: ILoanProcess[];
  dueDateDp: any;

  editForm = this.fb.group({
    id: [],
    order: [null, [Validators.required]],
    dueDate: [null, [Validators.required]],
    amount: [null, [Validators.required]],
    interest: [null, [Validators.required]],
    loanProcessId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected amortizationTableService: AmortizationTableService,
    protected loanProcessService: LoanProcessService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ amortizationTable }) => {
      this.updateForm(amortizationTable);
    });
    this.loanProcessService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILoanProcess[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILoanProcess[]>) => response.body)
      )
      .subscribe((res: ILoanProcess[]) => (this.loanprocesses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(amortizationTable: IAmortizationTable) {
    this.editForm.patchValue({
      id: amortizationTable.id,
      order: amortizationTable.order,
      dueDate: amortizationTable.dueDate,
      amount: amortizationTable.amount,
      interest: amortizationTable.interest,
      loanProcessId: amortizationTable.loanProcessId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const amortizationTable = this.createFromForm();
    if (amortizationTable.id !== undefined) {
      this.subscribeToSaveResponse(this.amortizationTableService.update(amortizationTable));
    } else {
      this.subscribeToSaveResponse(this.amortizationTableService.create(amortizationTable));
    }
  }

  private createFromForm(): IAmortizationTable {
    return {
      ...new AmortizationTable(),
      id: this.editForm.get(['id']).value,
      order: this.editForm.get(['order']).value,
      dueDate: this.editForm.get(['dueDate']).value,
      amount: this.editForm.get(['amount']).value,
      interest: this.editForm.get(['interest']).value,
      loanProcessId: this.editForm.get(['loanProcessId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAmortizationTable>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackLoanProcessById(index: number, item: ILoanProcess) {
    return item.id;
  }
}
