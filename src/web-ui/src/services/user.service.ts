import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private user = new Subject<User>();

  getUserObservable() {
    return this.user;
  }

  updateUserObservable(user: User) {
    this.user.next(user);
  }
}
