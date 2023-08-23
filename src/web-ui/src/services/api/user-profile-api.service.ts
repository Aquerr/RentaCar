import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../../app/app.consts';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../../models/user-profile.model';
import { ImageKind } from '../../enums/image.kind.enum';

@Injectable({
  providedIn: 'root'
})
export class UserProfileApiService {
  private URL = APP_BASE_URL + APP_V1_URL + '/profiles';

  constructor(private http: HttpClient) {
  }

  public saveProfile(user: UserProfile, image: File) {
    const formData = new FormData();
    console.log('image', image);
    if (image) {
      formData.append('image', image);
    }
    formData.append('profile', new Blob([JSON.stringify(user)], { type: 'application/json' }));
    return this.http.patch<UserProfile>(`${this.URL}/${user.id}`, formData);
  }

  public getProfile(profileId: number) {
    return this.http.get<UserProfile>(`${this.URL}/${profileId}`);
  }

}
