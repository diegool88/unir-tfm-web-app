import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WizardFooterService {
  private isFormValid = new Subject<boolean>();
  
  constructor() { }
  
  setFormValid(valid: boolean) {
      this.isFormValid.next(valid);
  }
  
  getFormValid(): Observable<boolean> {
      return this.isFormValid.asObservable();
  }
}
