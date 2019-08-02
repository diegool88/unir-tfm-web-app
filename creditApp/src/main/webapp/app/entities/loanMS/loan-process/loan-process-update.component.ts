import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILoanProcess, LoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from './loan-process.service';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { WarrantyService } from 'app/entities/loanMS/warranty';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity/banking-entity.service';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { ProductService } from 'app/entities/bankMS/product/product.service';
import { IProduct } from 'app/shared/model/bankMS/product.model';

@Component({
  selector: 'jhi-loan-process-update',
  templateUrl: './loan-process-update.component.html'
})
export class LoanProcessUpdateComponent implements OnInit {
  isSaving: boolean;

  warranties: IWarranty[];
  startDateDp: any;
  endDateDp: any;
  bankingEntities: IBankingEntity[];
  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z0-9s]+')]],
    requestedAmount: [null, [Validators.required]],
    givenAmount: [],
    loanPeriod: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    justification: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern('[A-Za-zs]+')]],
    debtorIdentification: [null, [Validators.required]],
    debtorIdentificationType: [null, [Validators.required]],
    debtorCountry: [null, [Validators.required]],
    bankingEntityMnemonic: [null, [Validators.required]],
    bankingProductMnemonic: [null, [Validators.required]],
    loanProcessStatus: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loanProcessService: LoanProcessService,
    protected warrantyService: WarrantyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected bankingEntityService: BankingEntityService,
    protected productService: ProductService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loanProcess }) => {
      this.updateForm(loanProcess);
    });
    this.warrantyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWarranty[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWarranty[]>) => response.body)
      )
      .subscribe((res: IWarranty[]) => (this.warranties = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.bankingEntityService
    .query()
    .pipe(
      filter((mayBeOk: HttpResponse<IBankingEntity[]>) => mayBeOk.ok),
      map((response: HttpResponse<IBankingEntity[]>) => response.body)
    )
    .subscribe(
      (res: IBankingEntity[]) => (this.bankingEntities = res),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(loanProcess: ILoanProcess) {
    this.editForm.patchValue({
      id: loanProcess.id,
      name: loanProcess.name,
      requestedAmount: loanProcess.requestedAmount,
      givenAmount: loanProcess.givenAmount,
      loanPeriod: loanProcess.loanPeriod,
      startDate: loanProcess.startDate,
      endDate: loanProcess.endDate,
      justification: loanProcess.justification,
      debtorIdentification: loanProcess.debtorIdentification,
      debtorIdentificationType: loanProcess.debtorIdentificationType,
      debtorCountry: loanProcess.debtorCountry,
      bankingEntityMnemonic: loanProcess.bankingEntityMnemonic,
      bankingProductMnemonic: loanProcess.bankingProductMnemonic,
      loanProcessStatus: loanProcess.loanProcessStatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const loanProcess = this.createFromForm();
    if (loanProcess.id !== undefined) {
      this.subscribeToSaveResponse(this.loanProcessService.update(loanProcess));
    } else {
      this.subscribeToSaveResponse(this.loanProcessService.create(loanProcess));
    }
  }

  private createFromForm(): ILoanProcess {
    return {
      ...new LoanProcess(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      requestedAmount: this.editForm.get(['requestedAmount']).value,
      givenAmount: this.editForm.get(['givenAmount']).value,
      loanPeriod: this.editForm.get(['loanPeriod']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      justification: this.editForm.get(['justification']).value,
      debtorIdentification: this.editForm.get(['debtorIdentification']).value,
      debtorIdentificationType: this.editForm.get(['debtorIdentificationType']).value,
      debtorCountry: this.editForm.get(['debtorCountry']).value,
      bankingEntityMnemonic: this.editForm.get(['bankingEntityMnemonic']).value,
      bankingProductMnemonic: this.editForm.get(['bankingProductMnemonic']).value,
      loanProcessStatus: this.editForm.get(['loanProcessStatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanProcess>>) {
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

  trackWarrantyById(index: number, item: IWarranty) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
