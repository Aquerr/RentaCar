import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationApiService } from '../../../services/api/authentication-api.service';
import {AbstractControl, FormBuilder, FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { ToastService, ToastType } from '../../../services/toast.service';
import { LanguageService } from '../../../services/language.service';
import {TranslatePipe} from "@ngx-translate/core";


@Component({
  selector: 'reactivation-account',
  templateUrl: './reactivation-account.component.html',
  imports: [
    TranslatePipe,
    ReactiveFormsModule
  ],
  styleUrls: ['./reactivation-account.component.scss']
})
export class ReactivationAccountComponent implements OnInit {
  form = new FormBuilder().group({
    login: new FormControl(null, [Validators.required])
  });

  constructor(private fb: FormBuilder,
              private router: Router,
              private toastService: ToastService,
              private languageService: LanguageService,
              private apiService: AuthenticationApiService) {}

  ngOnInit() {
    const url = this.router.routerState.snapshot.url;
    const login = url.substring(url.indexOf('/', 2) + 1, url.length);
    this.getLogin().setValue(login);
  }

  resendEmail() {
    const login = this.getLogin().value;
    this.apiService.resendActivationEmail(login).subscribe({
      next: () => this.router.navigate(['']).then(() => this.toastService.createToast(this.languageService.getMessage('components.reactivation-account.toasts.success'), ToastType.SUCCESS)),
      error: () => this.toastService.createToast(this.languageService.getMessage('components.reactivation-account.toasts.error'), ToastType.ERROR)
    });
  }

  getLogin(): AbstractControl {
    return this.form.get('login') as AbstractControl;
  }
}
