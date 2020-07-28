import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../shared/service/authentication.service';


@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.css']
})
export class BaseLayoutComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthenticationService) {
  }

  ngOnInit() {
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
