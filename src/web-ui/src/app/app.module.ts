import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignInComponent } from '../components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from '../components/not-found/not-found.component';
import { SharedModule } from '../modules/shared.module';
import { InfoComponent } from '../components/info/info.component';
import { NgxTranslateModule } from '../modules/ngx-translate.module';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    NotFoundComponent,
    InfoComponent
  ],
  imports: [BrowserModule, AppRoutingModule, SharedModule, NgxTranslateModule],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule {}
