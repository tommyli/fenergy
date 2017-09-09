import {Component, OnInit} from '@angular/core';
import {Principal} from '../model/principal';
import {AuthService} from '../service/auth.service';

@Component({
             selector: 'app-sign-in',
             templateUrl: './sign-in.component.html',
             styleUrls: ['./sign-in.component.css']
           })
export class SignInComponent implements OnInit {

  principal: Principal;
  authenticated = false;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.authenticated = false;
    this.authService.getCurrentPrincipal().subscribe(
      principal => {
        this.principal = principal;
        this.authenticated = true;
      },
      error => {
        this.authenticated = false;
        console.log(error);
      }
    );
  }

  signin(client: String) {
    this.authService.signin(client);
  }

  signout() {
    this.authService.signout();
    this.authenticated = false;
  }
}
