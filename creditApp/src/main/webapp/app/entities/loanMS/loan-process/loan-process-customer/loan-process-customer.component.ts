import { Component, OnInit, OnDestroy } from '@angular/core';
import { ILoanProcess } from "app/shared/model/loanMS/loan-process.model";
import { Subscription } from "rxjs";
import { LoanProcessService } from "app/entities/loanMS/loan-process";
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { AccountService } from "app/core";

@Component({
  selector: 'jhi-loan-process-customer',
  templateUrl: './loan-process-customer.component.html',
  styleUrls: ['./loan-process-customer.component.scss']
})
export class LoanProcessCustomerComponent implements OnInit, OnDestroy {
    loanProcesses: ILoanProcess[];
    currentAccount: any;
    eventSubscriber: Subscription;
    
    constructor(
      protected loanProcessService: LoanProcessService,
      protected jhiAlertService: JhiAlertService,
      protected eventManager: JhiEventManager,
      protected accountService: AccountService
    ) {}
    
    loadAll() {
      this.loanProcessService
        .query()
        .pipe(
          filter((res: HttpResponse<ILoanProcess[]>) => res.ok),
          map((res: HttpResponse<ILoanProcess[]>) => res.body)
        )
        .subscribe(
          (res: ILoanProcess[]) => {
            this.loanProcesses = res;
          },
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    
    ngOnInit() {
      this.loadAll();
      this.accountService.identity().then(account => {
        this.currentAccount = account;
      });
      this.registerChangeInLoanProcesses();
    }
    
    ngOnDestroy() {
      this.eventManager.destroy(this.eventSubscriber);
    }
    
    trackId(index: number, item: ILoanProcess) {
      return item.id;
    }
    
    registerChangeInLoanProcesses() {
      this.eventSubscriber = this.eventManager.subscribe('loanProcessListModification', response => this.loadAll());
    }
    
    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
}
