import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserProfile } from '../models/user-profile.model';
import { LanguageService } from '../services/language.service';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  iconLang = 'fi fi-us';
  subscription = new Subscription();
  userLogged: UserProfile | null = null;
  isMobile = false;

  constructor(
    private authenticationService: AuthenticationService,
    private languageService: LanguageService
  ) {
  }

  ngOnInit() {
    this.languageService.loadLanguage();
    this.setIconFlag(this.languageService.getLanguage() as string);
    this.authenticationService.setUserOnAppInitDispatch();
    this.startUserSubscription();
    this.isMobileView();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  startUserSubscription() {
    this.subscription = this.authenticationService.getUser().subscribe((userState) =>
      this.userLogged = userState
    );
  }

  changeLanguage() {
    if (this.iconLang === 'fi fi-us') {
      this.iconLang = 'fi fi-pl';
      this.languageService.setLanguage('pl');
    } else {
      this.iconLang = 'fi fi-us';
      this.languageService.setLanguage('en');
    }
    window.location.reload();
  }

  logout() {
    this.authenticationService.logoutDispatch();
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
