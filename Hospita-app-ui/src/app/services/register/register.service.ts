import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { AppConstants } from 'src/app/constants/app.constants';
import { environment } from '../../../environments/environment';
import { Register_form } from 'src/app/model/Register_form.model';
@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) {}

  saveMessage(user : Register_form){
    return this.http.post(environment.rooturl + AppConstants.REGISTER_API_URL,user,{ observe: 'response'});
  }
}
