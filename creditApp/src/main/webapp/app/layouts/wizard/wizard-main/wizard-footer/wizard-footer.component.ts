import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder } from "@angular/forms";
import { WizardService } from "app/layouts/wizard/wizard.service";
import { Router, ActivatedRoute } from "@angular/router";
import { WizardFooterService } from "app/layouts/wizard/wizard-footer.service";
import { Subscription } from "rxjs";

@Component({
  selector: 'jhi-wizard-footer',
  templateUrl: './wizard-footer.component.html',
  styleUrls: ['./wizard-footer.component.scss']
})
export class WizardFooterComponent implements OnInit, OnDestroy {

  steps: any[];
  index: number = 0;
  enableBackBtn: boolean;
  enableNextBtn: boolean;
  currentStep?: any;
  isFormValid: boolean = false;
  subscription: Subscription;

  constructor(private _formBuilder: FormBuilder, 
          private wizardService: WizardService, 
          private router: Router, 
          private route: ActivatedRoute,
          private wizardFooterService: WizardFooterService) { }

  ngOnInit() {
      this.steps = this.wizardService.getSteps();
      this.currentStep = this.wizardService.getCurrentStep();
      if(this.currentStep !== undefined)
          this.index = this.currentStep.index - 1;
      this.subscription = this.wizardFooterService.getFormValid().subscribe(isFormValid => {
          this.isFormValid = isFormValid ? true : false;
      });
      this.router.navigate(this.steps[this.index].path, { relativeTo: this.route, queryParams: this.steps[this.index].queryParams });
      this.processButtonAvailability();
  }
  
  ngOnDestroy() {
      // unsubscribe to ensure no memory leaks
      this.subscription.unsubscribe();
  }
  
  nextTask(){
      if((this.index + 1) <= this.steps.length - 1){
          this.router.navigate(this.steps[++this.index].path, { relativeTo: this.route });
          this.wizardService.setCurrentStep(this.steps[this.index]);
          this.processButtonAvailability();
      }
  }
  
  previousTask(){
      if((this.index - 1) >= 0){
          this.router.navigate(this.steps[--this.index].path, { relativeTo: this.route });
          this.wizardService.setCurrentStep(this.steps[this.index]);
          this.processButtonAvailability();
      }
  }
  
  processButtonAvailability(){
      this.enableBackBtn = this.index > 0;
      this.enableNextBtn = this.index < (this.steps.length - 1);
  }

}
