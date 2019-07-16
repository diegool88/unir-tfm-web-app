import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICity, City } from 'app/shared/model/regionMS/city.model';
import { CityService } from './city.service';
import { IStateProvince } from 'app/shared/model/regionMS/state-province.model';
import { StateProvinceService } from 'app/entities/regionMS/state-province';

@Component({
  selector: 'jhi-city-update',
  templateUrl: './city-update.component.html'
})
export class CityUpdateComponent implements OnInit {
  isSaving: boolean;

  stateprovinces: IStateProvince[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-zs]+')]],
    stateId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cityService: CityService,
    protected stateProvinceService: StateProvinceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ city }) => {
      this.updateForm(city);
    });
    this.stateProvinceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStateProvince[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStateProvince[]>) => response.body)
      )
      .subscribe((res: IStateProvince[]) => (this.stateprovinces = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(city: ICity) {
    this.editForm.patchValue({
      id: city.id,
      name: city.name,
      stateId: city.stateId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const city = this.createFromForm();
    if (city.id !== undefined) {
      this.subscribeToSaveResponse(this.cityService.update(city));
    } else {
      this.subscribeToSaveResponse(this.cityService.create(city));
    }
  }

  private createFromForm(): ICity {
    return {
      ...new City(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      stateId: this.editForm.get(['stateId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICity>>) {
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

  trackStateProvinceById(index: number, item: IStateProvince) {
    return item.id;
  }
}
