import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

@Component({
  selector: 'jhi-amortization-table-detail',
  templateUrl: './amortization-table-detail.component.html'
})
export class AmortizationTableDetailComponent implements OnInit {
  amortizationTable: IAmortizationTable;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ amortizationTable }) => {
      this.amortizationTable = amortizationTable;
    });
  }

  previousState() {
    window.history.back();
  }
}
