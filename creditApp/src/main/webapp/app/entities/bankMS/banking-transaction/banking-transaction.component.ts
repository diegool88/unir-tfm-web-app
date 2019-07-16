import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';
import { AccountService } from 'app/core';
import { BankingTransactionService } from './banking-transaction.service';

@Component({
  selector: 'jhi-banking-transaction',
  templateUrl: './banking-transaction.component.html'
})
export class BankingTransactionComponent implements OnInit, OnDestroy {
  bankingTransactions: IBankingTransaction[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected bankingTransactionService: BankingTransactionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.bankingTransactionService
      .query()
      .pipe(
        filter((res: HttpResponse<IBankingTransaction[]>) => res.ok),
        map((res: HttpResponse<IBankingTransaction[]>) => res.body)
      )
      .subscribe(
        (res: IBankingTransaction[]) => {
          this.bankingTransactions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBankingTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBankingTransaction) {
    return item.id;
  }

  registerChangeInBankingTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('bankingTransactionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
