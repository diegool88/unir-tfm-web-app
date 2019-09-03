import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBankingEntity, BankingEntity } from 'app/shared/model/bankMS/banking-entity.model';
import { BankingEntityService } from './banking-entity.service';

@Component({
  selector: 'jhi-banking-entity-update',
  templateUrl: './banking-entity-update.component.html'
})
export class BankingEntityUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    mnemonic: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('[A-Z0-9]+')]],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(40), Validators.pattern('[A-Za-z0-9\\s]+')]],
    description: [null, [Validators.minLength(5), Validators.maxLength(60), Validators.pattern('[A-Za-z0-9\\s]+')]]
  });

  constructor(protected bankingEntityService: BankingEntityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bankingEntity }) => {
      this.updateForm(bankingEntity);
    });
  }

  updateForm(bankingEntity: IBankingEntity) {
    this.editForm.patchValue({
      id: bankingEntity.id,
      mnemonic: bankingEntity.mnemonic,
      name: bankingEntity.name,
      description: bankingEntity.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bankingEntity = this.createFromForm();
    if (bankingEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.bankingEntityService.update(bankingEntity));
    } else {
      this.subscribeToSaveResponse(this.bankingEntityService.create(bankingEntity));
    }
  }

  private createFromForm(): IBankingEntity {
    return {
      ...new BankingEntity(),
      id: this.editForm.get(['id']).value,
      mnemonic: this.editForm.get(['mnemonic']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankingEntity>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
