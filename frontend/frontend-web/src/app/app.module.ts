import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './views/home/home.component';
import { LoginComponent } from './views/login/login.component';
import { AppService } from './shared/service/app.service';
import { AuthenticationInterceptor } from './shared/interceptor/authentication.interceptor';
import { AppRoutingModule } from './app-routing.module';
import { LoginLayoutComponent } from './components/login-layout/login-layout.component';
import { BaseLayoutComponent } from './components/base-layout/base-layout.component';
import { SharedModule } from './shared/shared.module';


const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home'
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    LoginLayoutComponent,
    BaseLayoutComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    SharedModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
    AppService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
