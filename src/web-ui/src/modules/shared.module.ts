import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';
import { CheckboxModule } from 'primeng/checkbox';
import { AppInterceptor } from '../components/authentication/app.interceptor';
import { LoginGuard } from '../components/authentication/login.guard';
import { AnonymousGuard } from '../components/authentication/anonymous.guard';
import { TooltipModule } from 'primeng/tooltip';
import { BrowserModule } from '@angular/platform-browser';
import { TitleStrategy } from '@angular/router';
import { CustomPageTitleStrategy } from '../strategy/custom-page-title.strategy';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { provideEnvironmentNgxMask } from 'ngx-mask';

@NgModule({
  exports: [
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CommonModule,
    InputTextModule,
    ToastModule,
    MessagesModule,
    CheckboxModule,
    TooltipModule,
    BrowserModule,
    CalendarModule,
    DropdownModule
  ],
  providers: [
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true,
    },
    { provide: TitleStrategy,
      useClass: CustomPageTitleStrategy },
    LoginGuard,
    AnonymousGuard,
    provideEnvironmentNgxMask()
  ],
})
export class SharedModule {
}
