import { RouterModule, Routes } from '@angular/router';
import { inject, NgModule } from '@angular/core';
import { AppGuard } from '../../authentication/app.guard';
import { Auth } from '../../auth.enum';
import { AdminAddVehicleComponent } from './add-vehicle/admin-add-vehicle.component';

const ADMIN_PANEL_ROUTES: Routes = [
  {
    path: 'admin-add-vehicle',
    component: AdminAddVehicleComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated() && inject(AppGuard).hasUserAuthority(Auth.VIEW_ADMIN_PANEL)],
    title: 'title.admin-add-vehicle'
  }
];

@NgModule({
  imports: [RouterModule.forChild(ADMIN_PANEL_ROUTES)],
  exports: [RouterModule]
})
export class AdminPanelRoutingModule {

}
