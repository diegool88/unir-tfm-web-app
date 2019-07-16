import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStateProvince, StateProvince } from 'app/shared/model/regionMS/state-province.model';
import { StateProvinceService } from './state-province.service';
import { ICountry } from 'app/shared/model/regionMS/country.model';
import { CountryService } from 'app/entities/regionMS/country';

@Component({
  selector: 'jhi-state-province-update',
  templateUrl: './state-province-update.component.html'
})
export class StateProvinceUpdateComponent implements OnInit {
  isSaving: boolean;

  countries: ICountry[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-zs]+')]],
    countryId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stateProvinceService: StateProvinceService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stateProvince }) => {
      this.updateForm(stateProvince);
    });
    this.countryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICountry[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICountry[]>) => response.body)
      )
      .subscribe((res: ICountry[]) => (this.countries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stateProvince: IStateProvince) {
    this.editForm.patchValue({
      id: stateProvince.id,
      name: stateProvince.name,
      countryId: stateProvince.countryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stateProvince = this.createFromForm();
    if (stateProvince.id !== undefined) {
      this.subscribeToSaveResponse(this.stateProvinceService.update(stateProvince));
    } else {
      this.subscribeToSaveResponse(this.stateProvinceService.create(stateProvince));
    }
  }

  private createFromForm(): IStateProvince {
    return {
      ...new StateProvince(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      countryId: this.editForm.get(['countryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateProvince>>) {
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

  trackCountryById(index: number, item: ICountry) {
    return item.id;
  }
}
