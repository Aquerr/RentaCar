import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../services/authorization.service';
import { SignInFormService } from './sign-in.form.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  form!: FormGroup;

  constructor(
    public formService: SignInFormService,
    private authenticationService: AuthenticationService,
  ) {}

  ngOnInit() {
    this.form = this.formService.getForm();
  }

  signIn() {
    const request = this.formService.createUserSignInRequest();
    this.authenticationService.signinUser(request);
  }
}
