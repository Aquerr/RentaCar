import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../../../../services/authentication.service';
import { UserProfile } from '../../../../models/user-profile.model';
import { FormGroup } from '@angular/forms';
import { ContactStepReservationFormService } from './contact-step-reservation.form.service';

@Component({
  selector: 'contact-step-reservation',
  templateUrl: './contact-step-reservation.component.html',
  styleUrls: ['./contact-step-reservation.component.scss']
})
export class ContactStepReservationComponent implements OnInit, OnDestroy {
  userProfile: UserProfile | null = null;
  form: FormGroup;
  subscription: Subscription = new Subscription();

  constructor(private formService: ContactStepReservationFormService,
              private authenticationService: AuthenticationService) {
    this.form = this.formService.getForm();
  }

  ngOnInit() {
    this.getUser();
  }

  getUser() {
    this.subscription = this.authenticationService.getUser().subscribe({
      next: (userProfile) => this.setUser(userProfile)
    });
  }

  setUser(userProfile: UserProfile | null) {
    this.userProfile = userProfile;
    if (userProfile) {
      this.loadFormData(userProfile);
    }
  }

  loadFormData(user: UserProfile) {
    this.formService.setForm(this.form, user);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  hasFormError(controlName: string, errorName: string) {
    const control = this.form.get(controlName);
    return control?.hasError(errorName) && control?.touched;
  }
}
