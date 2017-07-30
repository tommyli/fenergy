import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
             selector: 'app-sign-in',
             templateUrl: './sign-in.component.html',
             styleUrls: ['./sign-in.component.css']
           })
export class SignInComponent implements OnInit {

  authenticated = false;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {

  }

  signin(client: String) {
    console.log(`signing in ${client}`)
    this.authenticated = true;
    // this.http.get(`/login/${client}`).subscribe(data => {
    this.http.get(`http://localhost:8080/auth/signin/${client}`).subscribe(data => {
      console.log(`signed in ${data}`)
    });
  }

  signout() {
    this.http.post(`/auth/logout`, {}).subscribe(
      data => {
        this.authenticated = false;
        console.log("Signed out successfully.")
      },
      error => {
        console.log(`Unable to sign out due to error: ${error}`)
      }
    );
  }
}
