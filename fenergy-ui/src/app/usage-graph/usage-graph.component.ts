import {AfterViewInit, Component, Input, OnInit} from '@angular/core';

declare var Plotly: any;

@Component({
             selector: 'app-usage-graph',
             templateUrl: './usage-graph.component.html',
             styleUrls: ['./usage-graph.component.css']
           })
export class UsageGraphComponent implements OnInit, AfterViewInit {

  @Input() data: any;
  @Input() layout: any;
  @Input() options: any;
  @Input() displayRawData: boolean;

  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    Plotly.newPlot('div-usage-graph',
                   [{
                     x: [1, 2, 3, 4, 5],
                     y: [1, 2, 4, 8, 16]
                   }],
                   {margin: {t: 0}});

  }
}
