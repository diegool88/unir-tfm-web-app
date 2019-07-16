import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonalReference } from 'app/shared/model/personal-reference.model';

@Component({
  selector: 'jhi-personal-reference-detail',
  templateUrl: './personal-reference-detail.component.html'
})
export class PersonalReferenceDetailComponent implements OnInit {
  personalReference: IPersonalReference;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personalReference }) => {
      this.personalReference = personalReference;
    });
  }

  previousState() {
    window.history.back();
  }
}
