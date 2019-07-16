import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStateProvince } from 'app/shared/model/regionMS/state-province.model';

@Component({
  selector: 'jhi-state-province-detail',
  templateUrl: './state-province-detail.component.html'
})
export class StateProvinceDetailComponent implements OnInit {
  stateProvince: IStateProvince;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stateProvince }) => {
      this.stateProvince = stateProvince;
    });
  }

  previousState() {
    window.history.back();
  }
}
