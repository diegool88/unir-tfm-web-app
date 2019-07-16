import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IWarranty } from 'app/shared/model/loanMS/warranty.model';
import { AccountService } from 'app/core';
import { WarrantyService } from './warranty.service';

@Component({
  selector: 'jhi-warranty',
  templateUrl: './warranty.component.html'
})
export class WarrantyComponent implements OnInit, OnDestroy {
  warranties: IWarranty[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected warrantyService: WarrantyService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.warrantyService
      .query()
      .pipe(
        filter((res: HttpResponse<IWarranty[]>) => res.ok),
        map((res: HttpResponse<IWarranty[]>) => res.body)
      )
      .subscribe(
        (res: IWarranty[]) => {
          this.warranties = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInWarranties();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWarranty) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInWarranties() {
    this.eventSubscriber = this.eventManager.subscribe('warrantyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
