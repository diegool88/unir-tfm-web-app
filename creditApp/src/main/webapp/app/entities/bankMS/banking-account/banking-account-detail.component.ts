import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';

@Component({
  selector: 'jhi-banking-account-detail',
  templateUrl: './banking-account-detail.component.html'
})
export class BankingAccountDetailComponent implements OnInit {
  bankingAccount: IBankingAccount;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bankingAccount }) => {
      this.bankingAccount = bankingAccount;
    });
  }

  previousState() {
    window.history.back();
  }
}
