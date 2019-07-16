import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFee, Fee } from 'app/shared/model/bankMS/fee.model';
import { FeeService } from './fee.service';
import { IProduct } from 'app/shared/model/bankMS/product.model';
import { ProductService } from 'app/entities/bankMS/product';

@Component({
  selector: 'jhi-fee-update',
  templateUrl: './fee-update.component.html'
})
export class FeeUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    mnemonic: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('[A-Z0-9]+')]],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z0-9s]+')]],
    description: [],
    interestRate: [],
    uniqueValue: [],
    products: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected feeService: FeeService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fee }) => {
      this.updateForm(fee);
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fee: IFee) {
    this.editForm.patchValue({
      id: fee.id,
      mnemonic: fee.mnemonic,
      name: fee.name,
      description: fee.description,
      interestRate: fee.interestRate,
      uniqueValue: fee.uniqueValue,
      products: fee.products
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fee = this.createFromForm();
    if (fee.id !== undefined) {
      this.subscribeToSaveResponse(this.feeService.update(fee));
    } else {
      this.subscribeToSaveResponse(this.feeService.create(fee));
    }
  }

  private createFromForm(): IFee {
    return {
      ...new Fee(),
      id: this.editForm.get(['id']).value,
      mnemonic: this.editForm.get(['mnemonic']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      interestRate: this.editForm.get(['interestRate']).value,
      uniqueValue: this.editForm.get(['uniqueValue']).value,
      products: this.editForm.get(['products']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFee>>) {
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

  trackProductById(index: number, item: IProduct) {
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
