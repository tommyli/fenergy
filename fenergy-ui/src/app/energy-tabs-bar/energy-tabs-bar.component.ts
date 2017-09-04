import {Component, OnInit} from '@angular/core';

@Component({
             selector: 'app-energy-tabs-bar',
             templateUrl: './energy-tabs-bar.component.html',
             styleUrls: ['./energy-tabs-bar.component.css']
           })

export class EnergyTabsBarComponent implements OnInit {

  readonly TAB_ROUTES = [
    {'label': 'Dashboard', 'route': '/dashboard'},
    {'label': 'Consumption', 'route': '/usage'},
    {'label': 'Solar', 'route': '/solar'},
    {'label': 'Battery', 'route': '/battery'}
  ];

  constructor() {
  }

  ngOnInit() {
  }

}
