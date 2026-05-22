import {ApplicationConfig, provideAppInitializer, provideZonelessChangeDetection} from "@angular/core";
import {provideTranslateHttpLoader} from "@ngx-translate/http-loader";
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {provideRouter, TitleStrategy, withRouterConfig} from "@angular/router";
import {routes} from "./app.routes";
import {provideNgxMask} from "ngx-mask";
import {provideStore} from "@ngrx/store";
import {provideEffects} from "@ngrx/effects";
import {provideTranslateService} from "@ngx-translate/core";
import {MessageService} from "primeng/api";
import {reducers} from "./state/app.reducers";
import {CustomPageTitleStrategy} from "./strategy/custom-page-title.strategy";
import {DateService} from "./services/date.service";
import {DatePipe} from "@angular/common";

export const appConfig: ApplicationConfig = {
  providers: [
    provideTranslateService({fallbackLang: 'pl', loader: provideTranslateHttpLoader({prefix: './assets/i18n/', useHttpBackend: true, suffix: '.json'})}),
    provideHttpClient(withInterceptorsFromDi()),
    provideAppInitializer(() => {}),
    provideZonelessChangeDetection(),
    provideRouter(routes, withRouterConfig({paramsInheritanceStrategy: 'always'})),
    provideNgxMask(),
    provideStore(reducers, { initialState: {auth: {}}}),
    provideEffects(),
    DateService,
    DatePipe,
    MessageService,
    {
      provide: TitleStrategy,
      useClass: CustomPageTitleStrategy
    }
  ]
}
