import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignInComponent } from '../components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from '../components/not-found/not-found.component';
import { SharedModule } from '../modules/shared.module';
import { SearcherComponent } from '../components/searcher/searcher.component';
import { NgxTranslateModule } from '../modules/ngx-translate.module';
import { NgOptimizedImage } from '@angular/common';
import { ProfilePanelComponent } from '../components/profile-panel/profile-panel.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    NotFoundComponent,
    SearcherComponent,
    ProfilePanelComponent
  ],
  imports: [BrowserModule, AppRoutingModule, SharedModule, NgxTranslateModule, NgOptimizedImage],
  bootstrap: [AppComponent]
})
export class AppModule {}
