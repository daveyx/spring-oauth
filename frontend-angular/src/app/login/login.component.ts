import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../service/authentication.service';

@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent {

  public credentials: any = {username: '', password: ''};

  constructor(private authService: AuthenticationService,
              private router: Router) {
  }

  public login(): void {
    this.authService.login(this.credentials.username, this.credentials.password).subscribe(() => {
      this.router.navigateByUrl('/').then();
    });
  }
}
