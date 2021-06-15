import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from "rxjs/operators";
import { Router } from "@angular/router";

import { AuthenticationService } from '../service/authentication.service';


@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);


  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // add authorization header with auth token if available
    const token = this.authenticationService.getAuthToken();
    if (token && !this.isRefreshing) {
      request = this.addToken(request, token);
    }

    return next.handle(request).pipe(catchError(error => {
      if (error instanceof HttpErrorResponse && request.url.endsWith(AuthenticationService.AUTH_URL)) {
        if (error instanceof HttpErrorResponse && error.status === 401 || error.status === 400) {
          // 401 = refresh token expired
          // 400 = refresh token no mappable by server
          this.authenticationService.authenticated = false;
          this.authenticationService.removeAuthToken();
          this.authenticationService.removeRefreshToken();
          this.isRefreshing = false;
          this.router.navigate(['/login']).then();
          return throwError('refreshtoken expired');
        }
      } else if (error.status === 401) {
        return this.handle401Error(request, next);
      } else {
        return throwError(error);
      }
    }));
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        'Authorization': `Bearer ${token}`
      }
    });
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.authenticationService.refreshToken().pipe(
        switchMap((token: any) => {
          this.isRefreshing = false;
          this.refreshTokenSubject.next(token);
          return next.handle(this.addToken(request, token.access_token));
        }));
    } else {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap((token: any) => {
          return next.handle(this.addToken(request, token.access_token));
        }));
    }
  }

}
