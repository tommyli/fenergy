import {Component, OnInit} from '@angular/core';
import {Site} from '../model/site';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
             selector: 'app-site-list',
             templateUrl: './site-list.component.html',
             styleUrls: ['./site-list.component.css']
           })
export class LoginNmiListComponent implements OnInit {

  form: FormGroup;
  sites: Array<Site>;

  constructor(http: HttpClient) {
  }

  ngOnInit() {
    this.sites = [
      new Site('6123456789', 'Lyndhurst'),
      new Site('6987654321', 'Madison')
    ];
    this.form = new FormGroup({
                                loginNmi: new FormControl(this.sites[0].displayLabel)
                              });
  }
}
