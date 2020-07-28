import { Component } from '@angular/core';

import { Router } from '@angular/router';
import { AuthenticationService } from '../../shared/service/authentication.service';


@Component({
    selector: 'app-tab1',
    templateUrl: 'tab1.page.html',
    styleUrls: ['tab1.page.scss']
})
export class Tab1Page {

    constructor(
        private router: Router,
        private authService: AuthenticationService) {
    }

    public login(form) {
        console.log('-->');
        // this.authService.login(form.value).subscribe((res) => {
        //     this.router.navigateByUrl('home');
        // });
    }

}
