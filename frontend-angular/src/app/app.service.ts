import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './service/authentication.service';

@Injectable()
export class AppService {

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {
  }

  getResource(): Observable<any> {
    if (this.authenticationService.authenticated) {
      return this.http.get('http://localhost:8080/api/resource');
    } else {
      return this.http.get('http://localhost:8080/api/public-resource');
    }
  }
}
