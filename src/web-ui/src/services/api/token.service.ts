import {Injectable} from "@angular/core";
import {StorageService} from "../storage.service";
import * as moment from "moment";

@Injectable({
  providedIn: 'root',
})
export class TokenService {

  constructor(private storageService: StorageService) {
  }

  saveToken(token: string, rememberMe: boolean) {
    // rememberMe 86400 (1 day) else 3600 (1 hour)
    const expiresAt = moment().add(rememberMe ? 86400 : 3600, 'second');
    this.storageService.saveItem('JWT', token);
    this.storageService.saveItem('JWT_expire', JSON.stringify(expiresAt));
  }

  getToken() {
    const token = this.storageService.getItem('JWT');
    if (token && this.isTokenValid()) {
      return token;
    }
    this.removeToken();
    return null;
  }

  removeToken() {
    this.storageService.deleteItem('JWT');
    this.storageService.deleteItem('JWT_expire');
  }

  private isTokenValid(): boolean {
    return moment().isBefore(this.getExpiration());
  }

  private getExpiration() {
    const expiration = this.storageService.getItem("JWT_expire");
    if (expiration) {
      const expiresAt = JSON.parse(expiration);
      return moment(expiresAt);
    }
    return 0;
  }

}
