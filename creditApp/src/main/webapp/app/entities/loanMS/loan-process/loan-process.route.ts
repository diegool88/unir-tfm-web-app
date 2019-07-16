import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { LoanProcessService } from './loan-process.service';
import { LoanProcessComponent } from './loan-process.component';
import { LoanProcessDetailComponent } from './loan-process-detail.component';
import { LoanProcessUpdateComponent } from './loan-process-update.component';
import { LoanProcessDeletePopupComponent } from './loan-process-delete-dialog.component';
import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';

@Injectable({ providedIn: 'root' })
export class LoanProcessResolve implements Resolve<ILoanProcess> {
  constructor(private service: LoanProcessService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoanProcess> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LoanProcess>) => response.ok),
        map((loanProcess: HttpResponse<LoanProcess>) => loanProcess.body)
      );
    }
    return of(new LoanProcess());
  }
}

export const loanProcessRoute: Routes = [
  {
    path: '',
    component: LoanProcessComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsLoanProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoanProcessDetailComponent,
    resolve: {
      loanProcess: LoanProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsLoanProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoanProcessUpdateComponent,
    resolve: {
      loanProcess: LoanProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsLoanProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoanProcessUpdateComponent,
    resolve: {
      loanProcess: LoanProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsLoanProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const loanProcessPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LoanProcessDeletePopupComponent,
    resolve: {
      loanProcess: LoanProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsLoanProcess.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
