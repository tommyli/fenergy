// Tommy Li (tommy.li@firefire.co), 2017-09-16

import 'rxjs/add/operator/do';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';

@Injectable()
export class LoggingInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.info(`Request: ${req.method} ${req.urlWithParams} ${JSON.stringify(req.headers)}`);

    return next.handle(req)
      .do(event => {
        if (event instanceof HttpResponse) {
          console.info(`Response: ${event.status} ${event.statusText} ${JSON.stringify(event.headers)}`);
        }
      });
  }
}
