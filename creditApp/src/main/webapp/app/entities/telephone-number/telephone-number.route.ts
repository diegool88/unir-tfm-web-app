import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TelephoneNumber } from 'app/shared/model/telephone-number.model';
import { TelephoneNumberService } from './telephone-number.service';
import { TelephoneNumberComponent } from './telephone-number.component';
import { TelephoneNumberDetailComponent } from './telephone-number-detail.component';
import { TelephoneNumberUpdateComponent } from './telephone-number-update.component';
import { TelephoneNumberDeletePopupComponent } from './telephone-number-delete-dialog.component';
import { ITelephoneNumber } from 'app/shared/model/telephone-number.model';

@Injectable({ providedIn: 'root' })
export class TelephoneNumberResolve implements Resolve<ITelephoneNumber> {
  constructor(private service: TelephoneNumberService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITelephoneNumber> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TelephoneNumber>) => response.ok),
        map((telephoneNumber: HttpResponse<TelephoneNumber>) => telephoneNumber.body)
      );
    }
    return of(new TelephoneNumber());
  }
}

export const telephoneNumberRoute: Routes = [
  {
    path: '',
    component: TelephoneNumberComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'creditApp.telephoneNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TelephoneNumberDetailComponent,
    resolve: {
      telephoneNumber: TelephoneNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.telephoneNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TelephoneNumberUpdateComponent,
    resolve: {
      telephoneNumber: TelephoneNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.telephoneNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TelephoneNumberUpdateComponent,
    resolve: {
      telephoneNumber: TelephoneNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.telephoneNumber.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const telephoneNumberPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TelephoneNumberDeletePopupComponent,
    resolve: {
      telephoneNumber: TelephoneNumberResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.telephoneNumber.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
