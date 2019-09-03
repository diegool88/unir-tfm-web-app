import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../entities/customer/customer.service';
import { ICustomer, Customer } from 'app/shared/model/customer.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountService } from 'app/core';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { WizardService } from 'app/layouts/wizard/wizard.service';

@Component({
  selector: 'jhi-wizard',
  templateUrl: './wizard.component.html',
  styleUrls: ['./wizard.component.scss']
})
export class WizardComponent implements OnInit {
  currentAccount: any;
  customer?: ICustomer = new Customer();

  constructor(
    protected customerService: CustomerService,
    protected accountService: AccountService,
    protected jhiAlertService: JhiAlertService,
    protected wizardService: WizardService
  ) {}

  ngOnInit() {
    this.wizardService.setCustomer(undefined);
    this.wizardService.setCurrentStep(undefined);
    this.wizardService.clearSteps();
    this.accountService.identity().then(account => {
      if (account.authorities.includes('ROLE_USER') && !account.authorities.includes('ROLE_ADMIN')) {
        this.customerService
          .query()
          .pipe(
            filter((res: HttpResponse<ICustomer[]>) => res.ok),
            map((res: HttpResponse<ICustomer[]>) => res.body)
          )
          .subscribe(
            (res: ICustomer[]) => {
              this.customer = res.length > 0 ? res[0] : new Customer();
              this.wizardService.setCustomer(this.customer);
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
