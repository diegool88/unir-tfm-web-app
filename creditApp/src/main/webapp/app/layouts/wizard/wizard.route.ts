import { Route } from '@angular/router';

import { WizardComponent } from './wizard.component';

export const navbarRoute: Route = {
  path: '',
  component: WizardComponent,
  outlet: 'wizard'
};
