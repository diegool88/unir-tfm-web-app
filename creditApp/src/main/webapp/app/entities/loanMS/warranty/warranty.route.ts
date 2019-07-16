import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Warranty } from 'app/shared/model/loanMS/warranty.model';
import { WarrantyService } from './warranty.service';
import { WarrantyComponent } from './warranty.component';
import { WarrantyDetailComponent } from './warranty-detail.component';
import { WarrantyUpdateComponent } from './warranty-update.component';
import { WarrantyDeletePopupComponent } from './warranty-delete-dialog.component';
import { IWarranty } from 'app/shared/model/loanMS/warranty.model';

@Injectable({ providedIn: 'root' })
export class WarrantyResolve implements Resolve<IWarranty> {
  constructor(private service: WarrantyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWarranty> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Warranty>) => response.ok),
        map((warranty: HttpResponse<Warranty>) => warranty.body)
      );
    }
    return of(new Warranty());
  }
}

export const warrantyRoute: Routes = [
  {
    path: '',
    component: WarrantyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsWarranty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WarrantyDetailComponent,
    resolve: {
      warranty: WarrantyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsWarranty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WarrantyUpdateComponent,
    resolve: {
      warranty: WarrantyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsWarranty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WarrantyUpdateComponent,
    resolve: {
      warranty: WarrantyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsWarranty.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const warrantyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WarrantyDeletePopupComponent,
    resolve: {
      warranty: WarrantyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.loanMsWarranty.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
