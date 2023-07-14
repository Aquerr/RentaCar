import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';
import { LanguageService } from '../services/language.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  iconLang = 'fi fi-us';
  userSubscription = new Subscription();
  userLogged: User | undefined;

  constructor(
    private userService: UserService,
    private languageService: LanguageService,
  ) {}

  ngOnInit() {
    this.languageService.loadLanguage(this.userLogged);
    this.startUserSubscription();
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
  }

  startUserSubscription() {
    this.userSubscription = this.userService.getUserObservable().subscribe({
      next: (user) => {
        if (user) {
          this.userLogged = user;
          this.setLanguage(user);
        }
      },
    });
  }

  setLanguage(user: User | undefined) {
    this.languageService.loadLanguage(user);
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
}
