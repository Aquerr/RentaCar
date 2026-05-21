import {ApplicationConfig, provideAppInitializer, provideZonelessChangeDetection} from "@angular/core";
import {provideTranslateHttpLoader} from "@ngx-translate/http-loader";
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {provideRouter, withRouterConfig} from "@angular/router";
import {routes} from "./app.routes";
import {provideNgxMask} from "ngx-mask";
import {provideStore} from "@ngrx/store";
import {provideEffects} from "@ngrx/effects";
import {provideTranslateService} from "@ngx-translate/core";
import {MessageService} from "primeng/api";
import {reducers} from "./state/app.reducers";

export const appConfig: ApplicationConfig = {
  providers: [
    provideTranslateHttpLoader({prefix: './assets/i18n/', useHttpBackend: true, suffix: '.json'}),
    provideTranslateService({fallbackLang: 'pl'}),
    provideHttpClient(withInterceptorsFromDi()),
    provideAppInitializer(() => {}),
    provideZonelessChangeDetection(),
    provideRouter(routes, withRouterConfig({paramsInheritanceStrategy: 'always'})),
    provideNgxMask(),
    provideStore(reducers, { initialState: {auth: {}}}),
    provideEffects(),
    MessageService
  ]
}
