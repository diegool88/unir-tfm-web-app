import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IWarranty, Warranty } from 'app/shared/model/loanMS/warranty.model';
import { WarrantyService } from './warranty.service';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from 'app/entities/loanMS/loan-process';
import { ICustomer } from "app/shared/model/customer.model";
import { WizardService } from "app/layouts/wizard/wizard.service";

@Component({
  selector: 'jhi-warranty-update',
  templateUrl: './warranty-update.component.html'
})
export class WarrantyUpdateComponent implements OnInit {
  isSaving: boolean;

  loanprocesses: ILoanProcess[];
  mode: any;
  customer?: ICustomer;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z\\s]+')]],
    description: [],
    value: [null, [Validators.required]],
    document: [],
    documentContentType: [],
    debtorIdentification: [null, [Validators.required]],
    debtorIdentificationType: [null, [Validators.required]],
    debtorCountry: [null, [Validators.required]],
    loanProcesses: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected warrantyService: WarrantyService,
    protected loanProcessService: LoanProcessService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected wizardService: WizardService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ warranty }) => {
      this.updateForm(warranty);
    });
    this.activatedRoute.queryParams.subscribe(queryParams => {
      if (queryParams && queryParams.mode) {
        this.mode = queryParams.mode;
        if (this.mode === 'wizard') {
          this.customer = this.wizardService.getCustomer();
          this.loanProcessControl.setValidators(null);
          this.initializeCustomerInformation();
        }
      }
    });
    this.loanProcessService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILoanProcess[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILoanProcess[]>) => response.body)
      )
      .subscribe((res: ILoanProcess[]) => (this.loanprocesses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(warranty: IWarranty) {
    this.editForm.patchValue({
      id: warranty.id,
      name: warranty.name,
      description: warranty.description,
      value: warranty.value,
      document: warranty.document,
      documentContentType: warranty.documentContentType,
      debtorIdentification: warranty.debtorIdentification,
      debtorIdentificationType: warranty.debtorIdentificationType,
      debtorCountry: warranty.debtorCountry,
      loanProcesses: warranty.loanProcesses
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const warranty = this.createFromForm();
    if (this.mode === 'wizard'){
        this.wizardService.addWarranties(warranty);
        this.onSaveSuccess();
        return;
    }
    if (warranty.id !== undefined) {
      this.subscribeToSaveResponse(this.warrantyService.update(warranty));
    } else {
      this.subscribeToSaveResponse(this.warrantyService.create(warranty));
    }
  }

  private createFromForm(): IWarranty {
    return {
      ...new Warranty(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      value: this.editForm.get(['value']).value,
      documentContentType: this.editForm.get(['documentContentType']).value,
      document: this.editForm.get(['document']).value,
      debtorIdentification: this.editForm.get(['debtorIdentification']).value,
      debtorIdentificationType: this.editForm.get(['debtorIdentificationType']).value,
      debtorCountry: this.editForm.get(['debtorCountry']).value,
      loanProcesses: this.editForm.get(['loanProcesses']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWarranty>>) {
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
  
  protected initializeCustomerInformation(){
      this.editForm.patchValue({
        debtorIdentification: this.customer.identificationNumber,
        debtorIdentificationType: this.customer.identificationType,
        debtorCountry: this.customer.country
      });
  }
  
  get loanProcessControl(){
      return this.editForm.get('loanProcesses') as FormControl;
  }
}
