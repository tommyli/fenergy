// Tommy Li (tommy.li@firefire.co), 2017-09-07

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Principal} from '../model/principal';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class AuthService {

  principal: Principal = null;

  constructor(private http: HttpClient) {
  }

  getCurrentPrincipal(): Observable<Principal> {
    return this.http.get<Principal>('/auth/currentlogin')
      .catch((error: any) => {
        if (error.status === 401) {
          return Observable.throw(`Not authenticated: ${error.message}`);
        } else {
          return Observable.throw(`Unexpected server error: ${error.message}`);
        }
      });
  }

  signin(client: String) {
    window.location.href = `/auth/signin/${client}`;
  }

  signout() {
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
