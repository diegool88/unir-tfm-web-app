import { Component, OnInit } from '@angular/core';
import { FormBuilder } from "@angular/forms";
import { WizardService } from "app/layouts/wizard/wizard.service";
import { Router, ActivatedRoute } from "@angular/router";

@Component({
  selector: 'jhi-wizard-footer',
  templateUrl: './wizard-footer.component.html',
  styleUrls: ['./wizard-footer.component.scss']
})
export class WizardFooterComponent implements OnInit {

  steps: any[];
  index: number = 0;
  enableBackBtn: boolean;
  enableNextBtn: boolean;

  constructor(private _formBuilder: FormBuilder, private wizardService: WizardService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
      this.steps = this.wizardService.getSteps();
      this.router.navigate(this.steps[this.index].path, { relativeTo: this.route });
      this.enableBackBtn = false;
      this.enableNextBtn = true;
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
