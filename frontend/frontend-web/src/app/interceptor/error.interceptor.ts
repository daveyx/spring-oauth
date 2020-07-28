import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from "@angular/router";

import { AuthenticationService } from '../shared/service/authentication.service';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private authenticationService: AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        // remove authtoken if 401 response returned from api and redirect to login
        this.authenticationService.removeAuthToken();
        this.router.navigate(['login'], {queryParams: {returnUrl: this.router.url}}).then();
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    }));
  }
}
