import { Component, OnInit } from '@angular/core';
import { SignInFormService } from './sign-in.form.service';
import { FormGroup } from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';

@Component({
  selector: 'sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private authenticationService: AuthenticationService,
    public formService: SignInFormService
  ) {}

  ngOnInit() {
    this.form = this.formService.getForm();
  }

  signIn() {
    const request = this.formService.createUserSignInRequest();
    this.authenticationService.signInDispatch(request);
  }
}
