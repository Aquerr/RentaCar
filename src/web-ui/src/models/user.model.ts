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
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  birthDate: string;
  city: string;
  zipCode: string;
  street: string;
}
