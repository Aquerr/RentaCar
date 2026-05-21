import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserProfile } from './models/user-profile.model';
import { LanguageService } from './services/language.service';
import { AuthenticationService } from './services/authentication.service';
import { FontAwesomeLibraryService } from './services/font-awesome-library.service';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import {TranslatePipe} from "@ngx-translate/core";
import {Tooltip} from "primeng/tooltip";
import {ProfilePanelComponent} from "./components/profile/panel/profile-panel.component";
import {Toast} from "primeng/toast";
import {FooterComponent} from "./components/footer/footer.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  imports: [
    FaIconComponent,
    RouterLinkActive,
    TranslatePipe,
    RouterLink,
    Tooltip,
    ProfilePanelComponent,
    RouterOutlet,
    Toast,
    FooterComponent
  ],
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  iconLang = 'fi fi-us';
  subscription = new Subscription();
  userLogged: UserProfile | null = null;
  isMobile = false;
  sideMenuExpanded = false;

  constructor(
    private authenticationService: AuthenticationService,
    private languageService: LanguageService,
    private fontAwesomeLibrary: FontAwesomeLibraryService
  ) {
  }

  ngOnInit() {
    this.languageService.loadLanguage();
    this.setIconFlag(this.languageService.getLanguage() as string);
    this.authenticationService.setUserOnAppInitDispatch();
    this.startUserSubscription();
    this.isMobileView();
    this.fontAwesomeLibrary.setIcons();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  startUserSubscription() {
    this.subscription = this.authenticationService.getUser().subscribe((userState) =>
      this.userLogged = userState as UserProfile
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
    if (this.isMobile) {
      this.closeSideMenu();
    }
    this.authenticationService.logoutDispatch();
  }

  setIconFlag(lang: string) {
    if (lang === 'pl') {
      this.iconLang = 'fi fi-pl';
    } else {
      this.iconLang = 'fi fi-us';
    }
  }

  @HostListener('window:resize')
  isMobileView() {
    this.isMobile = window.innerWidth < 800;
  }


  toggleSideMenu() {
    this.sideMenuExpanded = !this.sideMenuExpanded;
  }

  closeSideMenu() {
    this.sideMenuExpanded = false;
  }
}
