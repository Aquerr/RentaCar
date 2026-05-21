import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, provideHttpClient} from '@angular/common/http';
import { CommonModule, DatePipe } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CheckboxModule } from 'primeng/checkbox';
import { AppInterceptor } from '../components/authentication/app.interceptor';
import { TooltipModule } from 'primeng/tooltip';
import { BrowserModule } from '@angular/platform-browser';
import { TitleStrategy } from '@angular/router';
import { CustomPageTitleStrategy } from '../strategy/custom-page-title.strategy';
import { provideEnvironmentNgxMask } from 'ngx-mask';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { DialogModule } from 'primeng/dialog';
import { DialogService } from 'primeng/dynamicdialog';
import { CarouselModule } from 'primeng/carousel';
import { AppGuard } from '../components/authentication/app.guard';
import { StepsModule } from 'primeng/steps';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ColorPickerModule } from 'primeng/colorpicker';
import { MultiSelectModule } from 'primeng/multiselect';
import {providePrimeNG} from "primeng/config";
import Aura from '@primeuix/themes/aura';

@NgModule({
  exports: [
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    InputTextModule,
    ToastModule,
    CheckboxModule,
    TooltipModule,
    BrowserModule,
    FontAwesomeModule,
    DialogModule,
    CarouselModule,
    StepsModule,
    ConfirmDialogModule,
    ColorPickerModule,
    MultiSelectModule
  ],
  providers: [
    provideHttpClient(),
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppInterceptor,
      multi: true
    },
    {
      provide: TitleStrategy,
      useClass: CustomPageTitleStrategy
    },
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    AppGuard,
    DatePipe,
    DialogService,
    ConfirmationService,
    provideEnvironmentNgxMask()
  ]
})
export class SharedModule {
}
