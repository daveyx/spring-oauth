import { Component } from '@angular/core';

import { AuthenticationService } from './service/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authService: AuthenticationService) {
  }

  public logout(): void {
    this.authService.logout();
  }

  public get isAuthenticated(): boolean {
    return this.authService.authenticated;
  }
}
