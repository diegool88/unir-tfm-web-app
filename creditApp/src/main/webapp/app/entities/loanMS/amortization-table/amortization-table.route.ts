import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';
import { AmortizationTableService } from './amortization-table.service';
import { AmortizationTableComponent } from './amortization-table.component';
import { AmortizationTableDetailComponent } from './amortization-table-detail.component';
import { AmortizationTableUpdateComponent } from './amortization-table-update.component';
import { AmortizationTableDeletePopupComponent } from './amortization-table-delete-dialog.component';
import { IAmortizationTable } from 'app/shared/model/loanMS/amortization-table.model';

@Injectable({ providedIn: 'root' })
export class AmortizationTableResolve implements Resolve<IAmortizationTable> {
  constructor(private service: AmortizationTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAmortizationTable> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AmortizationTable>) => response.ok),
        map((amortizationTable: HttpResponse<AmortizationTable>) => amortizationTable.body)
      );
    }
    return of(new AmortizationTable());
  }
}

export const amortizationTableRoute: Routes = [
  {
    path: '',
    component: AmortizationTableComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsAmortizationTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AmortizationTableDetailComponent,
    resolve: {
      amortizationTable: AmortizationTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsAmortizationTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AmortizationTableUpdateComponent,
    resolve: {
      amortizationTable: AmortizationTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsAmortizationTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AmortizationTableUpdateComponent,
    resolve: {
      amortizationTable: AmortizationTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsAmortizationTable.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const amortizationTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AmortizationTableDeletePopupComponent,
    resolve: {
      amortizationTable: AmortizationTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsAmortizationTable.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
