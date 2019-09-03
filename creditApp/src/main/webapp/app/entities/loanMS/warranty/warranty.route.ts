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
import { WarrantyDeletePopupTmpComponent } from "app/entities/loanMS/warranty/warranty-delete-dialog-tmp/warranty-delete-dialog-tmp.component";

@Injectable({ providedIn: 'root' })
export class WarrantyResolve implements Resolve<IWarranty> {
  constructor(private service: WarrantyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWarranty> {
    const id = route.params['id'] ? route.params['id'] : null;
    const index = route.params['index'] ? route.params['index'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Warranty>) => response.ok),
        map((warranty: HttpResponse<Warranty>) => warranty.body)
      );
    } else if (index) {
        let tmpWarranty = new Warranty();
        tmpWarranty.id = index;
        return of(tmpWarranty);
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
  },
  {
    path: ':index/deleteTmp',
    component: WarrantyDeletePopupTmpComponent,
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
