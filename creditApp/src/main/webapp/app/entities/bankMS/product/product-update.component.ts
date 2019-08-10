import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/bankMS/product.model';
import { ProductService } from './product.service';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { BankingEntityService } from 'app/entities/bankMS/banking-entity';
import { IFee } from 'app/shared/model/bankMS/fee.model';
import { FeeService } from 'app/entities/bankMS/fee';
import { ICurrency } from 'app/shared/model/bankMS/currency.model';
import { CurrencyService } from 'app/entities/bankMS/currency';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving: boolean;

  bankingentities: IBankingEntity[];

  fees: IFee[];

  currencies: ICurrency[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    mnemonic: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('[A-Z0-9]+')]],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z0-9\\s]+')]],
    description: [],
    category: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [],
    state: [null, [Validators.required]],
    interestRate: [],
    bankingEntityId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected bankingEntityService: BankingEntityService,
    protected feeService: FeeService,
    protected currencyService: CurrencyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
    });
    this.bankingEntityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBankingEntity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBankingEntity[]>) => response.body)
      )
      .subscribe((res: IBankingEntity[]) => (this.bankingentities = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.feeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFee[]>) => response.body)
      )
      .subscribe((res: IFee[]) => (this.fees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.currencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurrency[]>) => response.body)
      )
      .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
    this.editForm.patchValue({
      id: product.id,
      mnemonic: product.mnemonic,
      name: product.name,
      description: product.description,
      category: product.category,
      startDate: product.startDate,
      endDate: product.endDate,
      state: product.state,
      interestRate: product.interestRate,
      bankingEntityId: product.bankingEntityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      mnemonic: this.editForm.get(['mnemonic']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      category: this.editForm.get(['category']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      state: this.editForm.get(['state']).value,
      interestRate: this.editForm.get(['interestRate']).value,
      bankingEntityId: this.editForm.get(['bankingEntityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
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

  trackBankingEntityById(index: number, item: IBankingEntity) {
    return item.id;
  }

  trackFeeById(index: number, item: IFee) {
    return item.id;
  }

  trackCurrencyById(index: number, item: ICurrency) {
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
