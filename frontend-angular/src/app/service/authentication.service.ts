import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private static OAUTH_ENDPOINT = 'http://localhost:8080/oauth';
  private static AUTH_URL = '/token';
  private static CLIENT_ID = 'testjwtclientid';
  private static CLIENT_SECRET = 'XY7kmzoNzl100';
  private static LOGOUT_URL = '/logout';

  public authenticated = false;
  token: string;

  constructor(private apiService: ApiService) {
  }

  public login(username: string, password: string): Observable<object> {

    const loginSubject: Subject<object> = new Subject<object>();

    const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded')
      .set('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));

    // console.log('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));

    const options = {headers: httpHeaders};

    this.apiService.post(AuthenticationService.OAUTH_ENDPOINT + AuthenticationService.AUTH_URL, body, options).subscribe(res => {
        // console.log(res);
        if (res !== null && res.hasOwnProperty('access_token')) {
          // tslint:disable-next-line:no-string-literal
          this.token = res['access_token'];
          this.authenticated = true;
        }
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

  public logout(): Observable<object> {

    const logOutSubject: Subject<object> = new Subject<object>();
    this.apiService.get(AuthenticationService.LOGOUT_URL, AuthenticationService.OAUTH_ENDPOINT).subscribe(() => {
      this.authenticated = false;
      this.token = undefined;
      logOutSubject.next();
      logOutSubject.complete();
    });

    return logOutSubject;
  }
}
