import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from "@angular/common/http";
import {MdButtonModule, MdIconModule, MdToolbarModule} from '@angular/material';

import 'hammerjs';

import {NavBarComponent} from './nav-bar/nav-bar.component';
import {SignInComponent} from './sign-in/sign-in.component';

@NgModule({
            declarations: [
              AppComponent,
              NavBarComponent,
              SignInComponent
            ],
            imports: [
              BrowserModule,
              BrowserAnimationsModule,
              HttpClientModule,
              MdToolbarModule,
              MdIconModule,
              MdButtonModule
            ],
            providers: [],
            bootstrap: [AppComponent]
          })

export class AppModule {
}
