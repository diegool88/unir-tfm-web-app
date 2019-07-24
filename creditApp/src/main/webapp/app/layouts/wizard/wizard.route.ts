import { Routes } from '@angular/router';

import { WizardComponent } from './wizard.component';
import { WizardNavbarComponent } from './wizard-main/wizard-navbar/wizard-navbar.component';

/*Nuevos tomados de customer*/
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Customer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { CustomerComponent } from 'app/entities/customer/customer.component';
import { CustomerDetailComponent } from 'app/entities/customer/customer-detail.component';
import { CustomerUpdateComponent } from 'app/entities/customer/customer-update.component';
import { CustomerDeletePopupComponent } from 'app/entities/customer/customer-delete-dialog.component';
import { ICustomer } from 'app/shared/model/customer.model';


@Injectable({ providedIn: 'root' })
export class WizardResolve implements Resolve<ICustomer> {
  constructor(private service: CustomerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomer> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Customer>) => response.ok),
        map((customer: HttpResponse<Customer>) => customer.body)
      );
    }
    return of(new Customer());
  }
}

export const wizardRoute: Routes = [
  {
    path: 'wizard',
    component: WizardComponent,
    data: {
      authorities: ['ROLE_USER']
    }
  },
  {
    path: 'wizard-nav',
    component: WizardNavbarComponent,
    data: {
      authorities: ['ROLE_USER']
    },
    outlet: 'wizard'
  },
  {
    path: 'wizard-main/wizard-main-c-n',
    component: CustomerUpdateComponent,
    resolve: {
      customer: WizardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.customer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'wizard'
  },
  {
    path: 'wizard-main/customer/:id/edit',
    component: CustomerUpdateComponent,
    resolve: {
      customer: WizardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.customer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'wizard'
  }
];
