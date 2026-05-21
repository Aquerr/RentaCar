import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { TranslateModule } from '@ngx-translate/core';
import {provideTranslateHttpLoader} from "@ngx-translate/http-loader";
@NgModule({ declarations: [],
    exports: [TranslateModule], imports: [CommonModule,
        TranslateModule.forRoot({
          fallbackLang: 'en'
        })], providers: [provideTranslateHttpLoader({prefix: './assets/i18n/', useHttpBackend: true, suffix: '.json'}), provideHttpClient(withInterceptorsFromDi())] })
export class NgxTranslateModule {}
