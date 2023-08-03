import { Injectable } from '@angular/core';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faChair, faDharmachakra, faFan, faGasPump, faLightbulb, faOilCan } from '@fortawesome/free-solid-svg-icons';

@Injectable({
  providedIn: 'root'
})
export class FontAwesomeLibraryService {
  constructor(private library: FaIconLibrary) {

  }

  setIcons() {
    this.library.addIcons(
      faFan,
      faLightbulb,
      faChair,
      faDharmachakra,
      faGasPump,
      faOilCan
    );
  }
}
