import { Routes } from '@angular/router';

import { WizardComponent } from './wizard.component';
import { WizardMainComponent } from './wizard-main/wizard-main.component';
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
import { customerRoute, customerPopupRoute } from 'app/entities/customer/customer.route';
import { ICustomer } from 'app/shared/model/customer.model';
import { IAddress, Address } from 'app/shared/model/address.model';
import {
  AddressService,
  AddressComponent,
  AddressDetailComponent,
  AddressUpdateComponent,
  AddressDeletePopupComponent
} from 'app/entities/address';
import { IPersonalReference, PersonalReference } from 'app/shared/model/personal-reference.model';
import { PersonalReferenceService, PersonalReferenceComponent } from 'app/entities/personal-reference';
import { ITelephoneNumber, TelephoneNumber } from 'app/shared/model/telephone-number.model';
import { TelephoneNumberService, TelephoneNumberComponent } from 'app/entities/telephone-number';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { LoanProcessUpdateComponent, LoanProcessService } from 'app/entities/loanMS/loan-process';
import { ILoanProcess, LoanProcess } from 'app/shared/model/loanMS/loan-process.model';
import { WarrantyComponent, WarrantyUpdateComponent, WarrantyService } from 'app/entities/loanMS/warranty';
import { Warranty } from 'app/shared/model/loanMS/warranty.model';
import { WizardSummaryComponent } from './wizard-summary/wizard-summary.component';

@Injectable({ providedIn: 'root' })
export class WizardCustomerResolve implements Resolve<any> {
  constructor(
    private serviceCustomer: CustomerService,
    private serviceAddress: AddressService,
    private loanProcessService: LoanProcessService,
    private warratyService: WarrantyService
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (route.component === CustomerUpdateComponent) {
      if (id) {
        return this.serviceCustomer.find(id).pipe(
          filter((response: HttpResponse<Customer>) => response.ok),
          map((customer: HttpResponse<Customer>) => customer.body)
        );
      }
      return of(new Customer());
    } else if (route.component === LoanProcessUpdateComponent) {
      if (id) {
        return this.loanProcessService.find(id).pipe(
          filter((response: HttpResponse<LoanProcess>) => response.ok),
          map((loanProcess: HttpResponse<LoanProcess>) => loanProcess.body)
        );
      }
      return of(new LoanProcess());
    } else if (route.component === WarrantyUpdateComponent) {
      if (id) {
        return this.warratyService.find(id).pipe(
          filter((response: HttpResponse<Warranty>) => response.ok),
          map((warranty: HttpResponse<Warranty>) => warranty.body)
        );
      }
      return of(new Warranty());
    } else if (
      route.component === AddressComponent ||
      route.component === AddressDetailComponent ||
      route.component === AddressUpdateComponent ||
      route.component === AddressDeletePopupComponent
    ) {
      if (id) {
        return this.serviceAddress.find(id).pipe(
          filter((response: HttpResponse<Address>) => response.ok),
          map((customer: HttpResponse<Address>) => customer.body)
        );
      }
      return of(new Address());
    }
    return of({});
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
    }
  },
  {
    path: 'wizard-main',
    component: WizardMainComponent,
    children: [
      {
        path: 'customer/new',
        component: CustomerUpdateComponent,
        resolve: {
          customer: WizardCustomerResolve
        }
      },
      {
        path: 'customer/:id/edit',
        component: CustomerUpdateComponent,
        resolve: {
          customer: WizardCustomerResolve
        }
      },
      {
        path: 'address',
        component: AddressComponent,
        resolve: {
          pagingParams: JhiResolvePagingParams
        },
        data: {
          authorities: ['ROLE_USER'],
          defaultSort: 'id,asc',
          pageTitle: 'creditApp.address.home.title'
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: 'telephone-number',
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
        path: 'personal-reference',
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
        path: 'loan-process/new',
        component: LoanProcessUpdateComponent,
        resolve: {
          loanProcess: WizardCustomerResolve
        }
      },
      {
        path: 'warranty',
        component: WarrantyComponent,
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'creditApp.loanMsWarranty.home.title'
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: 'warranty/new',
        component: WarrantyUpdateComponent,
        resolve: {
          warranty: WizardCustomerResolve
        }
      },
      {
        path: 'summary',
        component: WizardSummaryComponent,
        data: {
          authorities: ['ROLE_USER']
        }
      }
    ],
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'creditApp.customer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
