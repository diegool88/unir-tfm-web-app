import { Component, OnInit } from '@angular/core';
import { WizardService } from "app/layouts/wizard/wizard.service";

@Component({
  selector: 'jhi-wizard-main',
  templateUrl: './wizard-main.component.html',
  styleUrls: ['./wizard-main.component.scss']
})
export class WizardMainComponent implements OnInit {

  steps: any[];

  constructor(private wizardService: WizardService) { }

  ngOnInit() {
      this.steps = this.wizardService.getSteps();
  }

}
