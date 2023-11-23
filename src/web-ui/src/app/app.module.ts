import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignInComponent } from '../components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from '../components/not-found/not-found.component';
import { SharedModule } from '../modules/shared.module';
import { NgxTranslateModule } from '../modules/ngx-translate.module';
import { NgOptimizedImage } from '@angular/common';
import { ProfilePanelComponent } from '../components/profile/panel/profile-panel.component';
import { ProfileEditComponent } from '../components/profile/edit/profile-edit.component';
import { NgxMaskDirective } from 'ngx-mask';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { AuthEffects } from '../state/auth/auth.effects';
import { metaReducers, reducers } from '../state/app.reducers';
import { CommonEffects } from '../state/common/common.effects';
import { SignUpComponent } from '../components/authentication/sign-up/sign-up.component';
import { VehicleCardComponent } from '../components/vehicles/vehicle-card/vehicle-card.component';
import { VehicleListComponent } from '../components/vehicles/vehicle-list/vehicle-list.component';
import { FooterComponent } from '../components/footer/footer.component';
import { ContactComponent } from '../components/contact/contact.component';
import { ReservationComponent } from '../components/reservation/reservation.component';
import { VehicleDetailsComponent } from '../components/vehicles/vehicle-details/vehicle-details.component';
import { AccountActivatedComponent } from '../components/info/account-activated/account-activated.component';
import {
  ActivationAccountComponent
} from '../components/authentication/activation-account/activation-account.component';
import {
  ReactivationAccountComponent
} from '../components/authentication/reactivation-account/reactivation-account.component';
import { MainComponent } from '../components/info/main/main.component';
import { AdminPanelComponent } from '../components/admin/admin-panel/admin-panel.component';
import {
  VehicleStepReservationComponent
} from '../components/reservation/steps/vehicle-step/vehicle-step-reservation.component';
import {
  ContactStepReservationComponent
} from '../components/reservation/steps/contact-step/contact-step-reservation.component';
import {
  SummaryStepReservationComponent
} from '../components/reservation/steps/summary-step/summary-step-reservation.component';
import { AdminSideMenuComponent } from '../components/admin/admin-panel/side-menu/admin-side-menu.component';
import { AdminAddVehicleComponent } from '../components/admin/admin-panel/add-vehicle/admin-add-vehicle.component';
import { PasswordResetComponent } from '../components/authentication/password-reset/password-reset.component';
import { NewPasswordComponent } from '../components/authentication/new-password/new-password.component';
import { ProfileMfaComponent } from '../components/profile/mfa/profile-mfa.component';
import { ProfileComponent } from '../components/profile/profile.component';
import { ProfileMfaTotpComponent } from '../components/profile/mfa/totp/profile-mfa-totp.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    NotFoundComponent,
    ProfilePanelComponent,
    ProfileEditComponent,
    SignUpComponent,
    VehicleCardComponent,
    VehicleListComponent,
    ContactComponent,
    FooterComponent,
    ReservationComponent,
    VehicleDetailsComponent,
    FooterComponent,
    ActivationAccountComponent,
    AccountActivatedComponent,
    ReactivationAccountComponent,
    MainComponent,
    AdminPanelComponent,
    VehicleStepReservationComponent,
    ContactStepReservationComponent,
    SummaryStepReservationComponent,
    AdminSideMenuComponent,
    AdminAddVehicleComponent,
    PasswordResetComponent,
    NewPasswordComponent,
    ProfileMfaComponent,
    ProfileComponent,
    ProfileMfaTotpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    NgxTranslateModule,
    NgOptimizedImage,
    NgxMaskDirective,
    BrowserModule,
    StoreModule.forRoot(reducers, {
      metaReducers
    }),
    EffectsModule.forRoot([AuthEffects, CommonEffects])
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
