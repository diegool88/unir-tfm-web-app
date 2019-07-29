import { Injectable } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { ICustomer, Customer } from "app/shared/model/customer.model";
import { CustomerService } from "app/entities/customer";
import { AccountService } from "app/core";
import { filter, map } from "rxjs/operators";
import { HttpResponse, HttpErrorResponse } from "@angular/common/http";
import { JhiAlertService } from "ng-jhipster";

@Injectable({
  providedIn: 'root'
})
export class WizardService {
  formArray: FormGroup[];
  customer?: ICustomer = new Customer();
  
  steps: any[];
  
  currentStep?: any;
  
  constructor(protected customerService: CustomerService,
          protected accountService: AccountService,
          protected jhiAlertService: JhiAlertService) { }
 
  
  addFormGroup(formGroup: FormGroup){
      this.formArray.push(formGroup);
  }
  
  getSteps(): any[] {
      return [{ index: 1, label: "creditApp.customer.detail.title", path: ['customer', this.customer.id, 'edit'], icon: "user" },
              { index: 2, label: "creditApp.address.home.title", path: ['address'], icon: "home" },
              { index: 3, label: "creditApp.telephoneNumber.home.title", path: ['telephone-number'], icon: "phone" },
              { index: 4, label: "creditApp.personalReference.home.title", path: ['personal-reference'], icon: "users" }];
  }
  
  setCustomer(customer: ICustomer){
      this.customer = customer;
  }
  
  getCurrentStep(){
      return this.currentStep;
  }
  
  setCurrentStep(step: any){
      this.currentStep = step;
  }
  
  getCustomer(): ICustomer{
      return this.customer;
  }
  
  protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
  }
}
