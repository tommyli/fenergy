import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MD_DIALOG_DATA, MdDialogRef} from '@angular/material';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

@Component({
             selector: 'app-upload-nem12',
             templateUrl: './upload-nem12.component.html',
             styleUrls: ['./upload-nem12.component.css']
           })
export class UploadNem12Component implements OnInit {

  form: FormGroup;

  constructor(public dialogRef: MdDialogRef<UploadNem12Component>, @Inject(MD_DIALOG_DATA) public data: any, private http: HttpClient) {
  }

  ngOnInit() {
    this.form = new FormGroup({
                                nem12Upload: new FormControl()
                              });
  }

  fileChange(event) {
    const fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      const file: File = fileList[0];
      console.log(`file: ${file}`);
      const formData: FormData = new FormData();
      formData.append('file', file, file.name);
      const headers = new Headers();

      /** No need to include Content-Type in Angular 4 */
      // headers.append('Content-Type', 'multipart/form-data');
      // headers.append('Accept', 'application/json');
      // const options = new RequestOptions({headers: headers});
      this.http.get(`/n12m/getUpload`)
        .map(res =>
               console.log(JSON.stringify(res))
        )
        .catch(error => Observable.throw(error))
        .subscribe(
          data => console.log('success'),
          error => console.log(error)
        );
      // this.http.post(`/api/postUpload`, formData)
      //   .map(res =>
      //          console.log(JSON.stringify(res))
      //   )
      //   .catch(error => Observable.throw(error))
      //   .subscribe(
      //     data => console.log('success'),
      //     error => console.log(error)
      //   );
    }
  }

  onCloseClick(): void {
    this.dialogRef.close();
  }
}
