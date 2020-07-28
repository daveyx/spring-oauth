import { Component, OnInit } from '@angular/core';

import { AppService } from '../../app.service';
import { AuthenticationService } from '../../shared/service/authentication.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  greeting: any;
  greeting2: any;

  constructor(private authenticationService: AuthenticationService, private appService: AppService) {
  }

  ngOnInit() {
    this.getData();
  }

  authenticated() {
    return this.authenticationService.authenticated;
  }

  public onRefresh(): void {
    this.getData();
    this.appService.getResource2().subscribe(data => this.greeting2 = data);
  }

  private getData(): void {
    this.greeting = null;
    this.greeting2 = null;
    this.appService.getResource().subscribe(data => this.greeting = data);
  }

}
