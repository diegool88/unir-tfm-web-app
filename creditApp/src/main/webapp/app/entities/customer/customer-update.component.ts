import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { CustomerService } from './customer.service';
import { IUser, UserService, AccountService } from 'app/core';
import { WizardFooterService } from "app/layouts/wizard/wizard-footer.service";

@Component({
  selector: 'jhi-customer-update',
  templateUrl: './customer-update.component.html'
})
export class CustomerUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];
  birthDateDp: any;
  clientSinceDp: any;
  currentAccount: any;
  mode: any;

  editForm = this.fb.group({
    id: [],
    firstname: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z\\s]+')]],
    secondName: [null, [Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z\\s]+')]],
    lastname: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z\\s]+')]],
    secondLastname: [null, [Validators.minLength(1), Validators.maxLength(30), Validators.pattern('[A-Za-z\\s]+')]],
    identificationType: [],
    identificationNumber: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20), Validators.pattern('[A-Z0-9]+')]],
    genre: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.pattern('[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}')]],
    birthDate: [null, [Validators.required]],
    country: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(20), Validators.pattern('[A-Za-z\\s]+')]],
    clientSince: [null, [Validators.required]],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerService: CustomerService,
    protected userService: UserService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private wizardFooterService: WizardFooterService
  ) {
      this.accountService.identity().then(account => {
        this.currentAccount = account;
      });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customer }) => {
      this.updateForm(customer);
    });
    this.activatedRoute.queryParams.subscribe(queryParams => {
        if(queryParams && queryParams.mode){
            this.mode = queryParams.mode;
            if(this.mode === "wizard"){
               this.wizardFooterService.setFormValid(false);
            }
        }
        
    });
    if (this.currentAccount.authorities.includes('ROLE_ADMIN')) {
        this.userService
        .query()
        .pipe(
          filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
          map((response: HttpResponse<IUser[]>) => response.body)
        )
        .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    } else {
        this.userService
        .find(this.currentAccount.login)
        .pipe(
          filter((mayBeOk: HttpResponse<IUser>) => mayBeOk.ok),
          map((response: HttpResponse<IUser>) => response.body) 
        )
        .subscribe((res: IUser) => (this.users = [res]), (res: HttpErrorResponse) => this.onError(res.message));
    }
    
  }

  updateForm(customer: ICustomer) {
    this.editForm.patchValue({
      id: customer.id,
      firstname: customer.firstname,
      secondName: customer.secondName,
      lastname: customer.lastname,
      secondLastname: customer.secondLastname,
      identificationType: customer.identificationType,
      identificationNumber: customer.identificationNumber,
      genre: customer.genre,
      email: customer.email,
      birthDate: customer.birthDate,
      country: customer.country,
      clientSince: customer.clientSince,
      userId: customer.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customer = this.createFromForm();
    if (customer.id !== undefined) {
      this.subscribeToSaveResponse(this.customerService.update(customer));
    } else {
      this.subscribeToSaveResponse(this.customerService.create(customer));
    }
  }

  private createFromForm(): ICustomer {
    return {
      ...new Customer(),
      id: this.editForm.get(['id']).value,
      firstname: this.editForm.get(['firstname']).value,
      secondName: this.editForm.get(['secondName']).value,
      lastname: this.editForm.get(['lastname']).value,
      secondLastname: this.editForm.get(['secondLastname']).value,
      identificationType: this.editForm.get(['identificationType']).value,
      identificationNumber: this.editForm.get(['identificationNumber']).value,
      genre: this.editForm.get(['genre']).value,
      email: this.editForm.get(['email']).value,
      birthDate: this.editForm.get(['birthDate']).value,
      country: this.editForm.get(['country']).value,
      clientSince: this.editForm.get(['clientSince']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomer>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
