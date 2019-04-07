import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router,
              private authService: AuthenticationService) {
  }

  public logout(): void {
    this.authService.logout().subscribe(() => {
      this.router.navigateByUrl('/login').then();
    });
  }

  public get isAuthenticated(): boolean {
    return this.authService.authenticated;
  }
}
