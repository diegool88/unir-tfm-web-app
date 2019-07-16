import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITelephoneNumber, TelephoneNumber } from 'app/shared/model/telephone-number.model';
import { TelephoneNumberService } from './telephone-number.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';

@Component({
  selector: 'jhi-telephone-number-update',
  templateUrl: './telephone-number-update.component.html'
})
export class TelephoneNumberUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  editForm = this.fb.group({
    id: [],
    countryCode: [null, [Validators.required]],
    regionCode: [null, [Validators.required]],
    number: [null, [Validators.required]],
    addressId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected telephoneNumberService: TelephoneNumberService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ telephoneNumber }) => {
      this.updateForm(telephoneNumber);
    });
    this.addressService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAddress[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAddress[]>) => response.body)
      )
      .subscribe((res: IAddress[]) => (this.addresses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(telephoneNumber: ITelephoneNumber) {
    this.editForm.patchValue({
      id: telephoneNumber.id,
      countryCode: telephoneNumber.countryCode,
      regionCode: telephoneNumber.regionCode,
      number: telephoneNumber.number,
      addressId: telephoneNumber.addressId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const telephoneNumber = this.createFromForm();
    if (telephoneNumber.id !== undefined) {
      this.subscribeToSaveResponse(this.telephoneNumberService.update(telephoneNumber));
    } else {
      this.subscribeToSaveResponse(this.telephoneNumberService.create(telephoneNumber));
    }
  }

  private createFromForm(): ITelephoneNumber {
    return {
      ...new TelephoneNumber(),
      id: this.editForm.get(['id']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      regionCode: this.editForm.get(['regionCode']).value,
      number: this.editForm.get(['number']).value,
      addressId: this.editForm.get(['addressId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITelephoneNumber>>) {
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

  trackAddressById(index: number, item: IAddress) {
    return item.id;
  }
}
