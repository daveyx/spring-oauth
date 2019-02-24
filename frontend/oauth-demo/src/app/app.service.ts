import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable()
export class AppService {

  authenticated = false;
  credentials: any;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, callback) {
    if (credentials) {
      this.credentials = btoa(credentials.username + ':' + credentials.password);
    }

    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('http://localhost:8080/user', {headers: headers}).subscribe(response => {
        if (response['name']) {
          this.authenticated = true;
        } else {
          this.authenticated = false;
        }
        return callback && callback();
      },
      error1 => {
        console.error('error /user ', error1)
      });
  }

  getResource(): Observable<any> {
    return this.http.get('http://localhost:8080/resource');
  }
}
