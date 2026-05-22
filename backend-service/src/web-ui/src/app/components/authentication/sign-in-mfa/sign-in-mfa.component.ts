import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {SignInMfaFormService} from "./sign-in-mfa.form.service";
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {TranslatePipe} from "@ngx-translate/core";
import {InputText} from "primeng/inputtext";

@Component({
  selector: 'app-sign-in-mfa',
  templateUrl: './sign-in-mfa.component.html',
  imports: [
    ReactiveFormsModule,
    TranslatePipe,
    InputText
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
