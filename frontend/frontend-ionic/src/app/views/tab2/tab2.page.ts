import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../shared/service/authentication.service';
import { AppService } from '../../shared/service/app.service';


@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page implements OnInit{

  greeting: any;
  greeting2: any;

  constructor(private authenticationService: AuthenticationService, private appService: AppService) {
  }

  public ngOnInit() {
    this.getData();
  }

  public authenticated(): boolean {
    return this.authenticationService.authenticated;
  }

  public onRefresh(): void {
    this.getData();
    this.appService.getResource2().subscribe(data => this.greeting2 = data);
  }

  public doAction(): void {
    console.log('--> ' + this.authenticated());
  }

  private getData(): void {
    this.greeting = null;
    this.greeting2 = null;
    this.appService.getResource().subscribe((data: object) => {
      this.greeting = data
    });
  }

}
