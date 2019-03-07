import {Component} from '@angular/core';
import {AppService} from '../app.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent {

  credentials = {username: '', password: ''};

  constructor(private app: AppService,
              private authService: AuthenticationService,
              private http: HttpClient,
              private router: Router) {
  }

  login() {
    this.authService.login(this.credentials.username, this.credentials.password);
    // this.app.authenticate(this.credentials, () => {
    //   this.router.navigateByUrl('/').then();
    // });
    // return false;
  }
}
