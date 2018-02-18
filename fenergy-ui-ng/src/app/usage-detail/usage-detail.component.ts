import {Component, Input, OnInit} from '@angular/core';

declare var Plotly: any;

@Component({
             selector: 'app-usage-detail',
             templateUrl: './usage-detail.component.html',
             styleUrls: ['./usage-detail.component.css']
           })
export class UsageDetailComponent implements OnInit {

  @Input() data: any;
  @Input() layout: any;
  @Input() options: any;
  @Input() displayRawData: boolean;

  constructor() {
  }

  ngOnInit() {
  }
}
