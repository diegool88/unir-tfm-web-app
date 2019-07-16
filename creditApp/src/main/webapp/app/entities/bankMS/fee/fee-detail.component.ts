import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFee } from 'app/shared/model/bankMS/fee.model';

@Component({
  selector: 'jhi-fee-detail',
  templateUrl: './fee-detail.component.html'
})
export class FeeDetailComponent implements OnInit {
  fee: IFee;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fee }) => {
      this.fee = fee;
    });
  }

  previousState() {
    window.history.back();
  }
}
