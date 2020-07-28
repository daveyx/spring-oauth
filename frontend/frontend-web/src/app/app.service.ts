import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthenticationService } from './service/authentication.service';
import { ApiService } from './service/api.service';


@Injectable()
export class AppService {

  constructor(private apiService: ApiService,
              private authenticationService: AuthenticationService) {
  }

  public getResource(): Observable<object> {
    if (this.authenticationService.authenticated) {
      return this.apiService.get('/resource');
    } else {
      return this.apiService.get('/public-resource');
    }
  }

  public getResource2(): Observable<object> {
    if (this.authenticationService.authenticated) {
      return this.apiService.get('/resource2');
    }
  }

}
