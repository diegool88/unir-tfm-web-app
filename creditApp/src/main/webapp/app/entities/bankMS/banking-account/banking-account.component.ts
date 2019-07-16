import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { AccountService } from 'app/core';
import { BankingAccountService } from './banking-account.service';

@Component({
  selector: 'jhi-banking-account',
  templateUrl: './banking-account.component.html'
})
export class BankingAccountComponent implements OnInit, OnDestroy {
  bankingAccounts: IBankingAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected bankingAccountService: BankingAccountService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.bankingAccountService
      .query()
      .pipe(
        filter((res: HttpResponse<IBankingAccount[]>) => res.ok),
        map((res: HttpResponse<IBankingAccount[]>) => res.body)
      )
      .subscribe(
        (res: IBankingAccount[]) => {
          this.bankingAccounts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBankingAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBankingAccount) {
    return item.id;
  }

  registerChangeInBankingAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('bankingAccountListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
