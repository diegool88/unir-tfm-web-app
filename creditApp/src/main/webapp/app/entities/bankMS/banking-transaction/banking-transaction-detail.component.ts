import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

@Component({
  selector: 'jhi-banking-transaction-detail',
  templateUrl: './banking-transaction-detail.component.html'
})
export class BankingTransactionDetailComponent implements OnInit {
  bankingTransaction: IBankingTransaction;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingTransaction }) => {
      this.bankingTransaction = bankingTransaction;
    });
  }

  previousState() {
    window.history.back();
  }
}
