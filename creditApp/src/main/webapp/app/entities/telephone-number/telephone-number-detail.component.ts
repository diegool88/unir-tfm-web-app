import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITelephoneNumber } from 'app/shared/model/telephone-number.model';

@Component({
  selector: 'jhi-telephone-number-detail',
  templateUrl: './telephone-number-detail.component.html'
})
export class TelephoneNumberDetailComponent implements OnInit {
  telephoneNumber: ITelephoneNumber;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ telephoneNumber }) => {
      this.telephoneNumber = telephoneNumber;
    });
  }

  previousState() {
    window.history.back();
  }
}
