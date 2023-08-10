import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from '../components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from '../components/not-found/not-found.component';
import { SearcherComponent } from '../components/searcher/searcher.component';
import { AnonymousGuard } from '../components/authentication/anonymous.guard';
import { ProfileEditComponent } from '../components/profile/edit/profile-edit.component';
import { LoginGuard } from '../components/authentication/login.guard';
import { SignUpComponent } from '../components/authentication/sign-up/sign-up.component';
import { VehicleListComponent } from '../components/vehicles/vehicle-list/vehicle-list.component';
import { ContactComponent } from '../components/contact/contact.component';
import { ReservationComponent } from '../components/reservation/reservation.component';
import {
  ActivationAccountComponent
} from '../components/authentication/activation-account/activation-account.component';
import { AccountActivatedComponent } from '../components/info/account-activated.component';
import {
  ReactivationAccountComponent
} from '../components/authentication/reactivation-account/reactivation-account.component';

const routes: Routes = [
  {
    path: '',
    component: SearcherComponent,
    title: 'title.main'
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [AnonymousGuard],
    title: 'title.sign-in'
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [AnonymousGuard],
    title: 'title.sign-up'
  },
  {
    path: 'profile-edit',
    component: ProfileEditComponent,
    canActivate: [LoginGuard],
    title: 'title.profile-edit'
  },
  {
    path: 'vehicle-list/:dates',
    component: VehicleListComponent,
    title: 'title.vehicle-list'
  },
  {
    path: 'contact',
    component: ContactComponent,
    title: 'title.contact'
  },
  {
    path: 'reservation/:id',
    component: ReservationComponent,
    canActivate: [LoginGuard],
    title: 'title.reservation'
  },
  {
    path: 'activation-account',
    component: ActivationAccountComponent,
    canActivate: [AnonymousGuard],
    title: 'title.activation'
  },
  {
    path: 'account-activated',
    component: AccountActivatedComponent,
    canActivate: [AnonymousGuard],
    title: 'title.account-activated'
  },
  {
    path: 'reactivation-account/:id',
    component: ReactivationAccountComponent,
    canActivate: [AnonymousGuard],
    title: 'title.reactivation-account'
  },
  {
    path: '**',
    component: NotFoundComponent,
    title: 'title.not-found'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
