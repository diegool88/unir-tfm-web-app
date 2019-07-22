import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../entities/customer/customer.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountService } from 'app/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-wizard',
  templateUrl: './wizard.component.html',
  styleUrls: ['./wizard.component.scss']
})
export class WizardComponent implements OnInit {

  currentAccount: any;
  customer: ICustomer;

  constructor(
          protected customerService: CustomerService,
          protected accountService: AccountService,
          protected jhiAlertService: JhiAlertService
          ) { }

  ngOnInit() {
      this.accountService.identity().then(account => {
        if(account.authorities.includes('ROLE_USER') && !account.authorities.includes('ROLE_ADMIN')){
            this.customerService
            .query()
            .pipe(
              filter((res: HttpResponse<ICustomer[]>) => res.ok),
              map((res: HttpResponse<ICustomer[]>) => res.body)
            )
            .subscribe(
              (res: ICustomer[]) => {
                this.customer = res[0];
              },
              (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
      });
  }
  
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

}
