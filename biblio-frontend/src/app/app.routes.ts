import { Routes } from '@angular/router';
import { ReferencementComponent } from './components/referencement/referencement.component';
import { CatalogueComponent } from './components/catalogue/catalogue.component';
import { TestScenarioComponent } from './components/test-scenario/test-scenario.component';

export const routes: Routes = [
  { path: '', redirectTo: '/catalogue', pathMatch: 'full' },
  { path: 'catalogue', component: CatalogueComponent },
  { path: 'referencer', component: ReferencementComponent },
  { path: 'test', component: TestScenarioComponent }
];
