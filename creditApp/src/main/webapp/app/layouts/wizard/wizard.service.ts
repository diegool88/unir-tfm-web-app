import { Injectable } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class WizardService {
  formArray: FormGroup[];
  
  constructor() { }
  
  addFormGroup(formGroup: FormGroup){
      this.formArray.push(formGroup);
  }
}
