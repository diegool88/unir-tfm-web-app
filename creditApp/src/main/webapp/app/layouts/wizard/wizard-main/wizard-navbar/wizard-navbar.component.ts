import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { WizardService } from "app/layouts/wizard/wizard.service";

@Component({
  selector: 'jhi-wizard-navbar',
  templateUrl: './wizard-navbar.component.html',
  styleUrls: ['./wizard-navbar.component.scss']
})
export class WizardNavbarComponent implements OnInit {

  steps: any[];
  currentStep?: any;
    
  constructor(private _formBuilder: FormBuilder, private wizardService: WizardService) { }

  ngOnInit() {
      this.steps = this.wizardService.getSteps();
      this.currentStep = this.wizardService.getCurrentStep();
  }
  
  updateStep(step: any){
      this.wizardService.setCurrentStep(step);
  }

}
