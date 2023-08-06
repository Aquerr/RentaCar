import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationApiService } from '../../../services/api/authentication-api.service';
import { ActivationRequest } from '../../../services/authentication.service';
import { ToastService, ToastType } from '../../../services/toast.service';
import { LanguageService } from '../../../services/language.service';


@Component({
  selector: 'activation-account',
  templateUrl: './activation-account.component.html'
})
export class ActivationAccountComponent implements OnInit {

  constructor(private router: Router,
              private toastService: ToastService,
              private languageService: LanguageService,
              private apiService: AuthenticationApiService) {}

  ngOnInit() {
    const url = this.router.routerState.snapshot.url;
    const token = url.substring(url.indexOf('=') + 1, url.length);
    const request = new ActivationRequest(token);
    this.apiService.activate(request).subscribe({
      next: () => this.router.navigate(['']).then(() => this.toastService.createToast(this.languageService.getMessage('components.activation-account.toasts.success'), ToastType.SUCCESS)),
      error: () => this.router.navigate(['']).then(() => this.toastService.createToast(this.languageService.getMessage('components.activation-account.toasts.error'), ToastType.ERROR))
    });
  }
}
