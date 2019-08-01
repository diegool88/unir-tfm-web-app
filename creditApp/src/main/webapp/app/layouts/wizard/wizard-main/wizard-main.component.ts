import { Component, OnInit, OnDestroy } from '@angular/core';
import { WizardService } from 'app/layouts/wizard/wizard.service';

@Component({
  selector: 'jhi-wizard-main',
  templateUrl: './wizard-main.component.html',
  styleUrls: ['./wizard-main.component.scss']
})
export class WizardMainComponent implements OnInit, OnDestroy {
  steps: any[];

  constructor(private wizardService: WizardService) {}

  ngOnInit() {
    this.steps = this.wizardService.getSteps();
  }

  ngOnDestroy() {
    console.log('WizardMainComponent Destroyed!!');
  }
}
