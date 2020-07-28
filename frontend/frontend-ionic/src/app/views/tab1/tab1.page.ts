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
        this.authService.login(form.value.email, form.value.password).subscribe((res) => {
            this.router.navigateByUrl('tabs/tab2').then();
        });
    }

}
