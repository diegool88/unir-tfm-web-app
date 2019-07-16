import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BankingAccount } from 'app/shared/model/bankMS/banking-account.model';
import { BankingAccountService } from './banking-account.service';
import { BankingAccountComponent } from './banking-account.component';
import { BankingAccountDetailComponent } from './banking-account-detail.component';
import { BankingAccountUpdateComponent } from './banking-account-update.component';
import { BankingAccountDeletePopupComponent } from './banking-account-delete-dialog.component';
import { IBankingAccount } from 'app/shared/model/bankMS/banking-account.model';

@Injectable({ providedIn: 'root' })
export class BankingAccountResolve implements Resolve<IBankingAccount> {
  constructor(private service: BankingAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBankingAccount> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BankingAccount>) => response.ok),
        map((bankingAccount: HttpResponse<BankingAccount>) => bankingAccount.body)
      );
    }
    return of(new BankingAccount());
  }
}

export const bankingAccountRoute: Routes = [
  {
    path: '',
    component: BankingAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BankingAccountDetailComponent,
    resolve: {
      bankingAccount: BankingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BankingAccountUpdateComponent,
    resolve: {
      bankingAccount: BankingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BankingAccountUpdateComponent,
    resolve: {
      bankingAccount: BankingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bankingAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BankingAccountDeletePopupComponent,
    resolve: {
      bankingAccount: BankingAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
