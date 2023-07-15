import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from '../components/authentication/sign-in/sign-in.component';
import { NotFoundComponent } from '../components/not-found/not-found.component';
import { InfoComponent } from '../components/info/info.component';
import {AnonymousGuard} from "../components/authentication/anonymous.guard";

const routes: Routes = [
  {
    path: '',
    component: InfoComponent,
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [AnonymousGuard]
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
