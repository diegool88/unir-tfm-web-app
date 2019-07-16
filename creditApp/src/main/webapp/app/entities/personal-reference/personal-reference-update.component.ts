import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonalReference, PersonalReference } from 'app/shared/model/personal-reference.model';
import { PersonalReferenceService } from './personal-reference.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-personal-reference-update',
  templateUrl: './personal-reference-update.component.html'
})
export class PersonalReferenceUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomer[];
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-zs]+')]],
    lastname: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-zs]+')]],
    genre: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.pattern('[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}')]],
    birthDate: [null, [Validators.required]],
    relation: [null, [Validators.required]],
    customerId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personalReferenceService: PersonalReferenceService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personalReference }) => {
      this.updateForm(personalReference);
    });
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(personalReference: IPersonalReference) {
    this.editForm.patchValue({
      id: personalReference.id,
      name: personalReference.name,
      lastname: personalReference.lastname,
      genre: personalReference.genre,
      email: personalReference.email,
      birthDate: personalReference.birthDate,
      relation: personalReference.relation,
      customerId: personalReference.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personalReference = this.createFromForm();
    if (personalReference.id !== undefined) {
      this.subscribeToSaveResponse(this.personalReferenceService.update(personalReference));
    } else {
      this.subscribeToSaveResponse(this.personalReferenceService.create(personalReference));
    }
  }

  private createFromForm(): IPersonalReference {
    return {
      ...new PersonalReference(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      lastname: this.editForm.get(['lastname']).value,
      genre: this.editForm.get(['genre']).value,
      email: this.editForm.get(['email']).value,
      birthDate: this.editForm.get(['birthDate']).value,
      relation: this.editForm.get(['relation']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonalReference>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
