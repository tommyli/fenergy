// Tommy Li (tommy.li@firefire.co), 2017-09-07

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Principal} from '../model/principal';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/isEmpty';
import 'rxjs/add/operator/do';
import 'rxjs/add/observable/throw';
import 'rxjs/add/observable/empty';

@Injectable()
export class AuthService {

  private principal: Observable<Principal> = Observable.empty();

  constructor(private http: HttpClient) {
  }

  get currentPrincipal(): Observable<Principal> {
    if (this.principal.isEmpty()) {
      this.principal = this.http.get<Principal>('/auth/currentlogin')
        .catch((error: any) => {
          console.log(JSON.stringify(error));
          if (error.status === 401) {
            return Observable.throw(`Not authenticated: ${error.message}`);
          } else {
            return Observable.throw(`Unexpected server error: [${error.status}], [${error.statusText}] ${error.message}`);
          }
        });

      return this.principal;
    } else {
      return this.principal;
    }
  }

  isAuthenticated(): boolean {
    return !this.principal.isEmpty();
  }

  signin(client: String) {
    window.location.href = `/auth/signin/${client}`;
  }

  signout() {
    // this.http.post(`/api/logout`, {}).subscribe(
    //   data => {
    //     console.log('Signed out resource server successfully');
    //   },
    //   error => {
    //     console.log(`Unable to sign out resource server due to error: ${error}`);
    //   }
    // );
    this.http.post(`/auth/logout`, {}).subscribe(
      data => {
        console.log('Signed out successfully');
      },
      error => {
        console.log(`Unable to sign out due to error: ${error}`);
      }
    );
  }
}
