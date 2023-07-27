import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { User } from '../models/user.model';
import { LanguageService } from '../services/language.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  iconLang = 'fi fi-us';
  userSubscription = new Subscription();
  userLogged: User | null = null;
  isMobile = false;

  constructor(
    private languageService: LanguageService,
    private authenticationService: AuthenticationService,
  ) {
  }

  ngOnInit() {
    this.authenticationService.trySetUserOnAppInit();
    this.languageService.loadLanguage();
    this.setIconFlag(this.languageService.getLanguage() as string);
    this.startUserSubscription();
    this.isMobileView();
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }

  startUserSubscription() {
    this.userSubscription = this.authenticationService
      .getUserObservable()
      .subscribe({
        next: (user) => {
          this.userLogged = user;
        },
      });
  }

  changeLanguage() {
    if (this.iconLang === 'fi fi-us') {
      this.iconLang = 'fi fi-pl';
      this.languageService.setLanguage('pl');
    } else {
      this.iconLang = 'fi fi-us';
      this.languageService.setLanguage('en');
    }
  }

  logout() {
    this.authenticationService.logout();
  }

  setIconFlag(lang: string) {
    if (lang === 'pl') {
      this.iconLang = 'fi fi-pl';
    } else {
      this.iconLang = 'fi fi-us';
    }
  }

  @HostListener('window:resize', ['$event'])
  isMobileView() {
    this.isMobile = window.innerWidth < 1200;
  }
}
