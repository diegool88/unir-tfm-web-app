import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWarranty } from 'app/shared/model/loanMS/warranty.model';

@Component({
  selector: 'jhi-warranty-detail',
  templateUrl: './warranty-detail.component.html'
})
export class WarrantyDetailComponent implements OnInit {
  warranty: IWarranty;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ warranty }) => {
      this.warranty = warranty;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
