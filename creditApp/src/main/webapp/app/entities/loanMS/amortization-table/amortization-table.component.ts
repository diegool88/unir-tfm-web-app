import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { AccountService } from 'app/core';
import { AmortizationTableService } from './amortization-table.service';

@Component({
  selector: 'jhi-amortization-table',
  templateUrl: './amortization-table.component.html'
})
export class AmortizationTableComponent implements OnInit, OnDestroy {
  amortizationTables: IAmortizationTable[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected amortizationTableService: AmortizationTableService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.amortizationTableService
      .query()
      .pipe(
        filter((res: HttpResponse<IAmortizationTable[]>) => res.ok),
        map((res: HttpResponse<IAmortizationTable[]>) => res.body)
      )
      .subscribe(
        (res: IAmortizationTable[]) => {
          this.amortizationTables = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAmortizationTables();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAmortizationTable) {
    return item.id;
  }

  registerChangeInAmortizationTables() {
    this.eventSubscriber = this.eventManager.subscribe('amortizationTableListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
