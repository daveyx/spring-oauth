import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { tap } from "rxjs/operators";

import { ApiService } from './api.service';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private static OAUTH_ENDPOINT = 'http://localhost:8080/oauth1';
  public static AUTH_URL = '/token';
  private static CLIENT_ID = 'testjwtclientid';
  private static CLIENT_SECRET = 'XY7kmzoNzl100';
  private static LOGOUT_URL = '/logout';
  private static AUTHTOKEN_KEY = 'access_token';
  private static REFRESHTOKEN_KEY = 'refresh_token';

  public authenticated = false;


  constructor(private apiService: ApiService) {
    this.authenticated = !!this.getAuthToken();
  }

  public login(username: string, password: string): Observable<object> {
    this.removeAuthToken();
    this.removeRefreshToken();
    const loginSubject: Subject<object> = new Subject<object>();

    const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;
    const httpHeaders = this.getHttpHeaders();

    // console.log('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));

    const options = {headers: httpHeaders};

    this.apiService.post(AuthenticationService.OAUTH_ENDPOINT + AuthenticationService.AUTH_URL, body, options).subscribe((res: any) => {
        // console.log(res);
        this.storeTokens(res);
        loginSubject.next();
        loginSubject.complete();
      },
      error1 => {
        console.log('--------------->');
        console.error(JSON.stringify(error1));
        loginSubject.error(JSON.stringify(error1));
      });

    return loginSubject;
  }

  private storeTokens(jwt: any) {
    if (jwt !== null && jwt.hasOwnProperty(AuthenticationService.AUTHTOKEN_KEY)) {
      this.authenticated = true;
      localStorage.setItem(AuthenticationService.AUTHTOKEN_KEY, jwt[AuthenticationService.AUTHTOKEN_KEY]);
    }
    if (jwt !== null && jwt.hasOwnProperty(AuthenticationService.REFRESHTOKEN_KEY)) {
      localStorage.setItem(AuthenticationService.REFRESHTOKEN_KEY, jwt[AuthenticationService.REFRESHTOKEN_KEY]);
    }
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
    .set('Content-Type', 'application/x-www-form-urlencoded')
    .set('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));
  }

  public logout(): Observable<object> {
    const logOutSubject: Subject<object> = new Subject<object>();
    this.apiService.get(AuthenticationService.LOGOUT_URL, AuthenticationService.OAUTH_ENDPOINT).subscribe(() => {
      this.authenticated = false;
      this.removeAuthToken();
      this.removeRefreshToken();
      logOutSubject.next();
      logOutSubject.complete();
    });

    return logOutSubject;
  }

  public getAuthToken(): string {
    return localStorage.getItem(AuthenticationService.AUTHTOKEN_KEY);
  }

  public removeAuthToken(): void {
    localStorage.removeItem(AuthenticationService.AUTHTOKEN_KEY);
  }

  private getRefreshToken(): string {
    return localStorage.getItem(AuthenticationService.REFRESHTOKEN_KEY);
  }

  public removeRefreshToken(): void {
    localStorage.removeItem(AuthenticationService.REFRESHTOKEN_KEY);
  }

  public refreshToken(): Observable<any> {
    const body = `grant_type=refresh_token&refresh_token=` + this.getRefreshToken();
    const httpHeaders = this.getHttpHeaders();
    const options = {headers: httpHeaders};
    return this.apiService.post(AuthenticationService.OAUTH_ENDPOINT + AuthenticationService.AUTH_URL, body, options)
      .pipe(tap((res: any) => {
          this.storeTokens(res);
        }
      ));
  }

}
