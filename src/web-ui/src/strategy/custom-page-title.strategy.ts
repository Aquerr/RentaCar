import { RouterStateSnapshot, TitleStrategy } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';
import { Injectable } from '@angular/core';

@Injectable()
export class CustomPageTitleStrategy extends TitleStrategy {
  constructor(private translateService: TranslateService,
              private readonly title: Title) {
    super();
  }

  override updateTitle(snapshot: RouterStateSnapshot): void {
    const title = this.buildTitle(snapshot);
    if (title) {
      this.translateService.get(title).subscribe((translatedTitle) => {
        this.title.setTitle(translatedTitle);
      })
    } else {
      this.title.setTitle('');
    }
  }
}
