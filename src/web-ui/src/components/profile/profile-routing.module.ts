import { inject, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileEditComponent } from './edit/profile-edit.component';
import { ProfileMfaComponent } from './mfa/profile-mfa.component';
import { AppGuard } from '../authentication/app.guard';
import { NotFoundComponent } from '../not-found/not-found.component';

const PROFILE_ROUTES: Routes = [
  {
    path: '',
    component: ProfileEditComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated()],
    title: 'title.profile-edit'
  },
  {
    path: 'profile-edit',
    component: ProfileEditComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated()],
    title: 'title.profile-edit'
  },
  {
    path: 'profile-mfa',
    component: ProfileMfaComponent,
    canActivate: [() => inject(AppGuard).isAuthenticated()],
    title: 'title.profile-mfa'
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
