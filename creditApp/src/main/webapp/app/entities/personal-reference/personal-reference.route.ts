import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonalReference } from 'app/shared/model/personal-reference.model';
import { PersonalReferenceService } from './personal-reference.service';
import { PersonalReferenceComponent } from './personal-reference.component';
import { PersonalReferenceDetailComponent } from './personal-reference-detail.component';
import { PersonalReferenceUpdateComponent } from './personal-reference-update.component';
import { PersonalReferenceDeletePopupComponent } from './personal-reference-delete-dialog.component';
import { IPersonalReference } from 'app/shared/model/personal-reference.model';

@Injectable({ providedIn: 'root' })
export class PersonalReferenceResolve implements Resolve<IPersonalReference> {
  constructor(private service: PersonalReferenceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonalReference> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonalReference>) => response.ok),
        map((personalReference: HttpResponse<PersonalReference>) => personalReference.body)
      );
    }
    return of(new PersonalReference());
  }
}

export const personalReferenceRoute: Routes = [
  {
    path: '',
    component: PersonalReferenceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'creditApp.personalReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonalReferenceDetailComponent,
    resolve: {
      personalReference: PersonalReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.personalReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonalReferenceUpdateComponent,
    resolve: {
      personalReference: PersonalReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.personalReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonalReferenceUpdateComponent,
    resolve: {
      personalReference: PersonalReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.personalReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personalReferencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonalReferenceDeletePopupComponent,
    resolve: {
      personalReference: PersonalReferenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.personalReference.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
