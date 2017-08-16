import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import 'hammerjs';

import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpModule} from '@angular/http';

import {
  MdButtonModule,
  MdGridListModule,
  MdIconModule,
  MdOptionModule,
  MdSelectModule,
  MdTabsModule,
  MdToolbarModule
} from '@angular/material';

import { FlexLayoutModule } from '@angular/flex-layout';
import {AppComponent} from './app.component';
import {MainViewComponent} from './main-view/main-view.component';
import {NavBarComponent} from './nav-bar/nav-bar.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {EnergyTabsBarComponent} from './energy-tabs-bar/energy-tabs-bar.component';
import {LoginNmiListComponent} from './login-nmi-list/login-nmi-list.component';

@NgModule({
            declarations: [
              AppComponent,
              NavBarComponent,
              SignInComponent,
              EnergyTabsBarComponent,
              LoginNmiListComponent,
              MainViewComponent
            ],
            imports: [
              BrowserAnimationsModule,
              HttpModule,
              BrowserModule,
              HttpClientModule,
              FlexLayoutModule,
              MdToolbarModule,
              MdTabsModule,
              MdSelectModule,
              MdOptionModule,
              MdGridListModule,
              MdIconModule,
              MdButtonModule
            ],
            exports: [
              HttpModule,
              BrowserModule,
              BrowserAnimationsModule,
              HttpClientModule,
              FlexLayoutModule,
              MdToolbarModule,
              MdGridListModule,
              MdTabsModule,
              MdSelectModule,
              MdOptionModule,
              MdIconModule,
              MdButtonModule
            ],
            providers: [],
            bootstrap: [AppComponent]
          })

export class AppModule {
}
