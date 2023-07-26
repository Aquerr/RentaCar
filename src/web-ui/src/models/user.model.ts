export class UserSignInRequest {
  login: string;
  password: string;
  rememberMe: boolean;

  constructor(login: string, password: string, rememberMe: boolean) {
    this.login = login;
    this.password = password;
    this.rememberMe = rememberMe;
  }
}

export interface User {
  id: number;
  login: string;
  password: string;
  lang: string;
}
