import { inject, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileEditComponent } from './edit/profile-edit.component';
import { authenticatedGuard } from '../authentication/authenticated-guard';
import { NotFoundComponent } from '../not-found/not-found.component';
import { ProfileSecurityComponent } from './security/profile-security.component';

const PROFILE_ROUTES: Routes = [
  {
    path: 'edit',
    component: ProfileEditComponent,
    canActivate: [() => inject(authenticatedGuard).isAuthenticated()],
    title: 'title.profile-edit'
  },
  {
    path: 'security',
    component: ProfileSecurityComponent,
    canActivate: [() => inject(authenticatedGuard).isAuthenticated()],
    title: 'title.profile-security'
  },
  {
    path: '**',
    component: NotFoundComponent,
    title: 'title.not-found'
  }
];

@NgModule({
  imports: [RouterModule.forChild(PROFILE_ROUTES)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {
}
