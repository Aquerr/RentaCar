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
import { ProfilePanelComponent } from '../components/profile/panel/profile-panel.component';
import { ProfileEditComponent } from '../components/profile/edit/profile-edit.component';
import { NgxMaskDirective } from 'ngx-mask';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    NotFoundComponent,
    SearcherComponent,
    ProfilePanelComponent,
    ProfileEditComponent
  ],
  imports: [BrowserModule, AppRoutingModule, SharedModule, NgxTranslateModule, NgOptimizedImage, NgxMaskDirective],
  bootstrap: [AppComponent]
})
export class AppModule {}
