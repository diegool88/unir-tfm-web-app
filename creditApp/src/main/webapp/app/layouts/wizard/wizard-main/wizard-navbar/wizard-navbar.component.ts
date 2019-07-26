import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'jhi-wizard-navbar',
  templateUrl: './wizard-navbar.component.html',
  styleUrls: ['./wizard-navbar.component.scss']
})
export class WizardNavbarComponent implements OnInit {

    isLinear = false;
    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit() {
      this.firstFormGroup = this._formBuilder.group({
          firstCtrl: ['', Validators.required]
        });
        this.secondFormGroup = this._formBuilder.group({
          secondCtrl: ['', Validators.required]
        });
  }

}
