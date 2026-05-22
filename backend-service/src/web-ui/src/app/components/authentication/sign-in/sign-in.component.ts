import { Component, OnInit } from '@angular/core';
import { SignInFormService } from './sign-in.form.service';
import {FormGroup, ReactiveFormsModule} from '@angular/forms';
import { AuthenticationService } from '../../../services/authentication.service';
import {TranslatePipe} from "@ngx-translate/core";
import {RouterLink} from "@angular/router";
import {InputComponent} from "../../shared/input/input.component";
import {ButtonComponent} from "../../shared/button/button.component";
import {CheckboxComponent} from "../../shared/checkbox/checkbox.component";

@Component({
  selector: 'sign-in',
  templateUrl: './sign-in.component.html',
  imports: [
    TranslatePipe,
    ReactiveFormsModule,
    RouterLink,
    InputComponent,
    ButtonComponent,
    CheckboxComponent
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
