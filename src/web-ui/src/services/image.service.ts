import { Injectable } from '@angular/core';
import { APP_BASE_URL, APP_V1_URL } from '../app/app.consts';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private URL = APP_BASE_URL + APP_V1_URL + '/images';
  private IMAGE_PATH_PREFIX = 'assets/images/';
  private DEFAULT_ICON_NAME = 'not-found.jpg';

  constructor(private http: HttpClient) {
  }

  getImagePath(url: string | undefined, imageType: ImageType) {
    const imgUrl = this.IMAGE_PATH_PREFIX + imageType + url;
    const iconNotFound = this.IMAGE_PATH_PREFIX + imageType + this.DEFAULT_ICON_NAME;
    return this.http.get(imgUrl, { responseType: 'text' })
    .pipe(
      map(() => imgUrl),
      catchError(() => of(iconNotFound)
      ));
  }

  saveImage(image: File, imagePath: string, imageType: ImageType) {
    const formData = new FormData();
    formData.append('image', image);
    formData.append('path', imageType + imagePath);
    return this.http.post<void>(this.URL, formData);
  }

  deleteImage(imagePath: string, imageType: ImageType) {
    const filePath = imageType + imagePath;
    const params = new HttpParams().set('path', filePath);
    return this.http.delete<void>(this.URL, { params: params });
  }

}

export enum ImageType {
  VEHICLES = 'vehicles/',
  USER = 'user/'
}
