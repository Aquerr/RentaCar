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

export class User {
  id: number;
  login: string;
  password: string;
  lang: string;

  constructor(id: number, login: string, password: string, lang: string) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.lang = lang;
  }
}
