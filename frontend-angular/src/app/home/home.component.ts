import { Component, OnInit } from '@angular/core';
import { AppService } from '../app.service';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  greeting = {};

  constructor(private authenticationService: AuthenticationService, private appService: AppService) {
  }

  ngOnInit() {
    this.appService.getResource().subscribe(data => this.greeting = data);
  }

  authenticated() {
    return this.authenticationService.authenticated;
  }
}
