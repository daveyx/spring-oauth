import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  static AUTH_URL = 'http://localhost:8080/oauth/token';
  static CLIENT_ID = 'testjwtclientid';
  static CLIENT_SECRET = 'XY7kmzoNzl100';

  public authenticated = false;

  constructor(private http: HttpClient) {
  }


  login(username: string, password: string) {
    const body = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}&grant_type=password`;
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/x-www-form-urlencoded')
      .set('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));

    console.log('Authorization', 'Basic ' + btoa(AuthenticationService.CLIENT_ID + ':' + AuthenticationService.CLIENT_SECRET));

    const options = {headers: httpHeaders};

    return this.http.post(AuthenticationService.AUTH_URL, body, options).subscribe(res => {
        console.log(res);
      },
      error1 => {
        console.log('--------------->');
        console.error(JSON.stringify(error1));
      });
  }

  logout() {

  }
}
