import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {SignInMfaFormService} from "./sign-in-mfa.form.service";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TranslatePipe} from "@ngx-translate/core";
import {InputComponent} from "../../shared/input/input.component";
import {ButtonComponent} from "../../shared/button/button.component";

@Component({
  selector: 'app-sign-in-mfa',
  templateUrl: './sign-in-mfa.component.html',
  imports: [
    ReactiveFormsModule,
    TranslatePipe,
    InputComponent,
    ButtonComponent
  ],
  styleUrls: ['./sign-in-mfa.component.scss']
})
export class SignInMfaComponent implements OnInit {
  form!: FormGroup;

  constructor(private activatedRoute: ActivatedRoute,
              private authenticationService: AuthenticationService,
              public formService: SignInMfaFormService) {}

  ngOnInit(): void {
    this.form = this.formService.getForm();
    this.activatedRoute.queryParams.subscribe(queryParams => {
      const challenge = queryParams["challenge"];
      this.form.get("challenge")?.setValue(challenge);
    });
  }

  signIn() {
    const request = this.formService.createMfaSignInRequest();
    this.authenticationService.signInMfaDispatch(request);
  }
}
