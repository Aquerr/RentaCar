import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class StorageService {

  getItem(key: string) {
    return localStorage.getItem(key);
  }

  saveItem(key: string, value: any) {
    localStorage.setItem(key, value);
  }

  deleteItem(key: string) {
    localStorage.removeItem(key);
  }

}
