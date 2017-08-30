import {AfterViewInit, Component, OnInit} from '@angular/core';

declare var Plotly: any;

@Component({
             selector: 'app-dashboard',
             templateUrl: './dashboard.component.html',
             styleUrls: ['./dashboard.component.css']
           })
export class DashboardComponent implements OnInit, AfterViewInit {

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    Plotly.newPlot('div-dashboard-detail',
                   [{
                     x: [1, 2, 3, 4, 5],
                     y: [1, 2, 4, 8, 16]
                   }],
                   {margin: {t: 0}});

  }
}
