import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DashboardComponent} from './dashboard/dashboard.component';
import {BatteryDetailComponent} from './battery-detail/battery-detail.component';
import {SolarDetailComponent} from './solar-detail/solar-detail.component';
import {UsageDetailComponent} from './usage-detail/usage-detail.component';

const ROUTES: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'signin/google', component: DashboardComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'usage', component: UsageDetailComponent},
  {path: 'solar', component: SolarDetailComponent},
  {path: 'battery', component: BatteryDetailComponent},
  {path: '**', redirectTo: '/dashboard'}
];

@NgModule({
            imports: [RouterModule.forRoot(ROUTES)],
            exports: [RouterModule]
          })
export class AppRoutingModule {
}
