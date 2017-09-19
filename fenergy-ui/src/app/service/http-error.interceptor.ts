// Tommy Li (tommy.li@firefire.co), 2017-09-16

import 'rxjs/add/operator/do';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {NotificationsService} from 'angular2-notifications/dist';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private notifications: NotificationsService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .catch((err: HttpErrorResponse) => {
        console.log(`HttpErrorResponse: ${JSON.stringify(err)}`);
        if (err.error instanceof Error) {
          console.error('Client error occurred:', err.error.message);
          this.notifications.error(
            'Client Error',
            `${err.error.message}`,
          );
        } else {
          console.error(`Server error, returned code ${err.status}, error: ${JSON.stringify(err.error)}`);
          this.notifications.error(
            'Server Error',
            `${err.error.error}`,
          );
        }

        return Observable.throw(err);
      });
  }
}
