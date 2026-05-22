import { Routes } from '@angular/router';
import { SignInComponent } from './components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SignUpComponent } from './components/authentication/sign-up/sign-up.component';
import { VehicleListComponent } from './components/vehicles/vehicle-list/vehicle-list.component';
import { ContactComponent } from './components/contact/contact.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import {
  ActivationAccountComponent
} from './components/authentication/activation-account/activation-account.component';
import { AccountActivatedComponent } from './components/info/account-activated/account-activated.component';
import {
  ReactivationAccountComponent
} from './components/authentication/reactivation-account/reactivation-account.component';
import { MainComponent } from './components/info/main/main.component';
import { VehicleDetailsComponent } from './components/vehicles/vehicle-details/vehicle-details.component';
import { AdminPanelComponent } from './components/admin/admin-panel/admin-panel.component';
import { authenticatedGuard } from './components/authentication/authenticated-guard';
import { Auth } from './components/auth.enum';
import { PasswordResetComponent } from './components/authentication/password-reset/password-reset.component';
import { NewPasswordComponent } from './components/authentication/new-password/new-password.component';
import { ProfileComponent } from './components/profile/profile.component';
import {SignInMfaComponent} from "./components/authentication/sign-in-mfa/sign-in-mfa.component";
import {
  ReservationPaymentInfoComponent
} from './components/reservation/payment-info/reservation-payment-info.component';
import { MyReservationsComponent } from './components/my-reservations/my-reservations.component';
import {notAuthenticatedGuard} from "./components/authentication/not-authenticated-guard";
import {hasAuthoritiesGuard} from "./components/authentication/has-authorities.guard";

export const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    title: 'title.main'
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.sign-in'
  },
  {
    path: 'sign-in-mfa',
    component: SignInMfaComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.sign-in-mfa'
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.sign-up'
  },
  {
    path: 'password-reset',
    component: PasswordResetComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.password-reset'
  },
  {
    path: 'new-password',
    component: NewPasswordComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.new-password'
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authenticatedGuard],
    loadChildren: () => import('./components/profile/profile-routing.module').then(module => module.ProfileRoutingModule),
    title: 'title.profile'
  },
  {
    path: 'vehicle-list',
    component: VehicleListComponent,
    title: 'title.vehicle-list'
  },
  {
    path: 'vehicle-details/:id',
    component: VehicleDetailsComponent,
    title: 'title.vehicle-details'
  },
  {
    path: 'contact',
    component: ContactComponent,
    title: 'title.contact'
  },
  {
    path: 'reservation/:id',
    component: ReservationComponent,
    canActivate: [authenticatedGuard],
    title: 'title.reservation'
  },
  {
    path: 'my-reservations',
    component: MyReservationsComponent,
    canActivate: [authenticatedGuard],
    title: 'title.myReservations'
  },
  {
    path: 'reservation-payment-info',
    component: ReservationPaymentInfoComponent,
    canActivate: [authenticatedGuard],
    title: 'title.reservation-payment-info'
  },
  {
    path: 'activation-account',
    component: ActivationAccountComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.activation'
  },
  {
    path: 'account-activated',
    component: AccountActivatedComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.account-activated'
  },
  {
    path: 'reactivation-account/:id',
    component: ReactivationAccountComponent,
    canActivate: [notAuthenticatedGuard],
    title: 'title.reactivation-account'
  },
  {
    path: 'admin-panel',
    component: AdminPanelComponent,
    canActivate: [authenticatedGuard,hasAuthoritiesGuard(Auth.VIEW_ADMIN_PANEL)],
    loadChildren: () => import('./components/admin/admin-panel/admin-panel-routing.module').then(module => module.AdminPanelRoutingModule),
    title: 'title.admin-panel'
  },
  {
    path: '**',
    component: NotFoundComponent,
    title: 'title.not-found'
  }
];
