import {Component, OnInit} from '@angular/core';

@Component({
             selector: 'app-main-view',
             templateUrl: './main-view.component.html',
             styleUrls: ['./main-view.component.css'],
           })
export class MainViewComponent implements OnInit {

  defaultNotificationOptions = {
    position: ['bottom', 'left'],
    timeOut: 0,
    showProgressBar: false,
    lastOnBottom: true,
    pauseOnHover: true,
    clickToClose: true
    // maxLength: 10
  };

  constructor() {
  }

  ngOnInit() {
  }

}
