import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanProcess } from 'app/shared/model/loanMS/loan-process.model';

@Component({
  selector: 'jhi-loan-process-detail',
  templateUrl: './loan-process-detail.component.html'
})
export class LoanProcessDetailComponent implements OnInit {
  loanProcess: ILoanProcess;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ loanProcess }) => {
      this.loanProcess = loanProcess;
    });
  }

  previousState() {
    window.history.back();
  }
}
