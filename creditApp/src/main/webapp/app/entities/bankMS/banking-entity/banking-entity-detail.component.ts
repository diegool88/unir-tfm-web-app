import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';

@Component({
  selector: 'jhi-banking-entity-detail',
  templateUrl: './banking-entity-detail.component.html'
})
export class BankingEntityDetailComponent implements OnInit {
  bankingEntity: IBankingEntity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingEntity }) => {
      this.bankingEntity = bankingEntity;
    });
  }

  previousState() {
    window.history.back();
  }
}
