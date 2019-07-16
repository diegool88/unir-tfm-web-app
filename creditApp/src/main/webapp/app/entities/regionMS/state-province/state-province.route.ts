import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StateProvince } from 'app/shared/model/regionMS/state-province.model';
import { StateProvinceService } from './state-province.service';
import { StateProvinceComponent } from './state-province.component';
import { StateProvinceDetailComponent } from './state-province-detail.component';
import { StateProvinceUpdateComponent } from './state-province-update.component';
import { StateProvinceDeletePopupComponent } from './state-province-delete-dialog.component';
import { IStateProvince } from 'app/shared/model/regionMS/state-province.model';

@Injectable({ providedIn: 'root' })
export class StateProvinceResolve implements Resolve<IStateProvince> {
  constructor(private service: StateProvinceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStateProvince> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StateProvince>) => response.ok),
        map((stateProvince: HttpResponse<StateProvince>) => stateProvince.body)
      );
    }
    return of(new StateProvince());
  }
}

export const stateProvinceRoute: Routes = [
  {
    path: '',
    component: StateProvinceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'creditApp.regionMsStateProvince.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StateProvinceDetailComponent,
    resolve: {
      stateProvince: StateProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.regionMsStateProvince.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StateProvinceUpdateComponent,
    resolve: {
      stateProvince: StateProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.regionMsStateProvince.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StateProvinceUpdateComponent,
    resolve: {
      stateProvince: StateProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.regionMsStateProvince.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stateProvincePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StateProvinceDeletePopupComponent,
    resolve: {
      stateProvince: StateProvinceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.regionMsStateProvince.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
