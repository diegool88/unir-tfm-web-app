import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';
import { BankingTransactionService } from './banking-transaction.service';
import { BankingTransactionComponent } from './banking-transaction.component';
import { BankingTransactionDetailComponent } from './banking-transaction-detail.component';
import { BankingTransactionUpdateComponent } from './banking-transaction-update.component';
import { BankingTransactionDeletePopupComponent } from './banking-transaction-delete-dialog.component';
import { IBankingTransaction } from 'app/shared/model/bankMS/banking-transaction.model';

@Injectable({ providedIn: 'root' })
export class BankingTransactionResolve implements Resolve<IBankingTransaction> {
  constructor(private service: BankingTransactionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBankingTransaction> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BankingTransaction>) => response.ok),
        map((bankingTransaction: HttpResponse<BankingTransaction>) => bankingTransaction.body)
      );
    }
    return of(new BankingTransaction());
  }
}

export const bankingTransactionRoute: Routes = [
  {
    path: '',
    component: BankingTransactionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BankingTransactionDetailComponent,
    resolve: {
      bankingTransaction: BankingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BankingTransactionUpdateComponent,
    resolve: {
      bankingTransaction: BankingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BankingTransactionUpdateComponent,
    resolve: {
      bankingTransaction: BankingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bankingTransactionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BankingTransactionDeletePopupComponent,
    resolve: {
      bankingTransaction: BankingTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingTransaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
