import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

class Principal {
  name: string;
  email: string;
  givenName: string;
  familyName: string;
  pictureUrl: string;
  locale: string;
}

@Component({
             selector: 'app-sign-in',
             templateUrl: './sign-in.component.html',
             styleUrls: ['./sign-in.component.css']
           })
export class SignInComponent implements OnInit {

  authenticated = false;
  name: string;
  pictureUrl: string;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.http.get<Principal>('/auth/currentlogin').subscribe(
      principal => {
        this.authenticated = true;
        this.name = principal.name;
        this.pictureUrl = principal.pictureUrl;
      },
      error => {
        console.log(`Error signing in: ${error}`);
      }
    );
  }

  signin(client: String) {
    console.log(`signing in ${client}`);
    window.location.href = `/auth/signin/${client}`;
  }

  signout() {
    this.http.post(`/auth/logout`, {}).subscribe(
      data => {
        this.authenticated = false;
        console.log('Signed out successfully');
      },
      error => {
        console.log(`Unable to sign out due to error: ${error}`);
      }
    );
  }
}
