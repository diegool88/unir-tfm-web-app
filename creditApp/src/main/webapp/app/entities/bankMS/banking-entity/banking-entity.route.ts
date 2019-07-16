import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { BankingEntityService } from './banking-entity.service';
import { BankingEntityComponent } from './banking-entity.component';
import { BankingEntityDetailComponent } from './banking-entity-detail.component';
import { BankingEntityUpdateComponent } from './banking-entity-update.component';
import { BankingEntityDeletePopupComponent } from './banking-entity-delete-dialog.component';
import { IBankingEntity } from 'app/shared/model/bankMS/banking-entity.model';

@Injectable({ providedIn: 'root' })
export class BankingEntityResolve implements Resolve<IBankingEntity> {
  constructor(private service: BankingEntityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBankingEntity> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BankingEntity>) => response.ok),
        map((bankingEntity: HttpResponse<BankingEntity>) => bankingEntity.body)
      );
    }
    return of(new BankingEntity());
  }
}

export const bankingEntityRoute: Routes = [
  {
    path: '',
    component: BankingEntityComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'creditApp.bankMsBankingEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BankingEntityDetailComponent,
    resolve: {
      bankingEntity: BankingEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BankingEntityUpdateComponent,
    resolve: {
      bankingEntity: BankingEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BankingEntityUpdateComponent,
    resolve: {
      bankingEntity: BankingEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingEntity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bankingEntityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BankingEntityDeletePopupComponent,
    resolve: {
      bankingEntity: BankingEntityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.bankMsBankingEntity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
