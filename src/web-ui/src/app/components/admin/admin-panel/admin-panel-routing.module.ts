import { RouterModule, Routes } from '@angular/router';
import { inject, NgModule } from '@angular/core';
import { AppGuard } from '../../authentication/app.guard';
import { Auth } from '../../auth.enum';
import { AdminAddVehicleComponent } from './add-vehicle/admin-add-vehicle.component';
import { AdminRemoveVehicleComponent } from './remove-vehicle/admin-remove-vehicle.component';

const ADMIN_PANEL_ROUTES: Routes = [
  {
    path: 'add-vehicle',
    component: AdminAddVehicleComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated() && inject(AppGuard).hasUserAuthority(Auth.ADD_VEHICLE)],
    title: 'title.admin-add-vehicle'
  },
  {
    path: 'remove-vehicle',
    component: AdminRemoveVehicleComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated() && inject(AppGuard).hasUserAuthority(Auth.REMOVE_VEHICLE)],
    title: 'title.admin-remove-vehicle'
  }
];

@NgModule({
  imports: [RouterModule.forChild(ADMIN_PANEL_ROUTES)],
  exports: [RouterModule]
})
export class AdminPanelRoutingModule {

}
