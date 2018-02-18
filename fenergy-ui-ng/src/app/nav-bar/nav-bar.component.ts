import {Component, OnInit} from '@angular/core';
import {MdDialog, MdDialogRef} from '@angular/material';
import {UploadNem12Component} from '../upload-nem12/upload-nem12.component';

@Component({
             selector: 'app-nav-bar',
             templateUrl: './nav-bar.component.html',
             styleUrls: ['./nav-bar.component.css']
           })
export class NavBarComponent implements OnInit {

  dialogRef: MdDialogRef<UploadNem12Component>;

  constructor(public uploadNem12Dialog: MdDialog) {
  }

  ngOnInit() {
  }

  openNem12UploadDialog() {
    this.dialogRef = this.uploadNem12Dialog.open(UploadNem12Component, {
      height: '300px',
      width: '500px',
    });
    this.dialogRef.afterClosed().subscribe(result => {
      this.dialogRef = null;
    });
  }
}
