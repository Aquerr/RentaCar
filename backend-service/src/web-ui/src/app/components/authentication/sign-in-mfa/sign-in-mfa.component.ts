import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../../../services/authentication.service";
import {SignInMfaFormService} from "./sign-in-mfa.form.service";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-sign-in-mfa',
  templateUrl: './sign-in-mfa.component.html',
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
