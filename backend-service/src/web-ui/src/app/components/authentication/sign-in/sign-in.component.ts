import { Component, OnInit } from '@angular/core';
import { SignInFormService } from './sign-in.form.service';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';
import {TranslatePipe} from "@ngx-translate/core";
import {Checkbox} from "primeng/checkbox";

@Component({
  selector: 'sign-in',
  templateUrl: './sign-in.component.html',
  imports: [
    TranslatePipe,
    ReactiveFormsModule,
    Checkbox
  ],
  styleUrls: ['./sign-in.component.scss']
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
