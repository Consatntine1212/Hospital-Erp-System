import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http'; // Ensure correct imports
import { AppConstants } from "../../constants/app.constants";
import { environment } from '../../../environments/environment';
import { User } from '../../model/user.model';
import { Contact } from '../../model/contact.model';
import { Appointments } from 'src/app/model/appointments.model';
import { Messages } from 'src/app/model/message.model';
import { Prescription } from 'src/app/model/prescriptions.model';
import { ScheduleEntry } from 'src/app/model/schedule-entry.model';
import { Observable } from 'rxjs'; // Ensure correct imports
import { Register_form } from 'src/app/model/Register_form.model';
@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http:HttpClient) { }

  getAccountDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.ACCOUNT_API_URL + "?id="+id,{ observe: 'response',withCredentials: true });
  }

  getAccountTransactions(id: String){
    return this.http.get(environment.rooturl + AppConstants.BALANCE_API_URL+ "?id="+id,{ observe: 'response',withCredentials: true });
  }

  getLoansDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.LOANS_API_URL+ "?id="+id,{ observe: 'response',withCredentials: true });
  }

  getCardsDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.CARDS_API_URL+ "?id="+id,{ observe: 'response',withCredentials: true });
  }

  getPatientDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.PATIENT_API_URL+ "?id="+id,{ observe: 'response',withCredentials: true });
  }
  
  getNoticeDetails(){
    return this.http.get(environment.rooturl + AppConstants.NOTICES_API_URL,{ observe: 'response' });
  }

  saveMessage(contact : Contact){
    return this.http.post(environment.rooturl + AppConstants.CONTACT_API_URL,contact,{ observe: 'response'});
  }



  //Appointment  
  getDoctorAppointmentsDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.DOCTORS_APPOINTMENTS_API_URL+ "?doctorId="+id,{ observe: 'response',withCredentials: true });
  }
  getPatientAppointmentsDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.PATIENT_APPOINTMENTS_API_URL+ "?patientId="+id,{ observe: 'response',withCredentials: true });
  }

  getAppointmentsByCriteria(){
    return this.http.get(environment.rooturl + AppConstants.SEARCH_APPOINTMENTS_BY_CRITIRIA_API_URL,{ observe: 'response',withCredentials: true });
  }
  saveAppointment(appointment : Appointments){
    return this.http.post(environment.rooturl + AppConstants.NEW_APPOINTMENT_API_URL,appointment,{ observe: 'response',withCredentials: true });
  }
  saveAppointmentByInfo(appointmentId: number, patientId: String, newDescription: string){
    return this.http.put(environment.rooturl + AppConstants.NEW_APPOINTMENT_BY_INFO_API_URL + "?appointmentId=" + appointmentId + "&patientId=" + patientId + "&newDescription=" + newDescription, { observe: 'response', withCredentials: true });
  }

  updateAppointment(appointment : Appointments){
    return this.http.post(environment.rooturl + AppConstants.UPDATE_APPOINTMENTS_API_URL,appointment,{ observe: 'response',withCredentials: true });
  }
  //Drugs
  getDrugsDetails(){
    return this.http.get(environment.rooturl + AppConstants.DRUGS_API_URL,{ observe: 'response' ,withCredentials: true });
  }
  getAvailableDrugsDetails(){
    return this.http.get(environment.rooturl + AppConstants.AVAILABLE_DRUGS_API_URL,{ observe: 'response' ,withCredentials: true });
  }
  //Scedule
  getMyScheduleDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.MYSCHEDULE_API_URL+ "?doctorId="+id,{ observe: 'response',withCredentials: true });
  }

  updateMyScheduleentry(id: String,scheduleEntry : ScheduleEntry){
    return this.http.post(environment.rooturl + AppConstants.UPDATE_MY_SCHEDULE+ "?doctorId="+id,scheduleEntry,{ observe: 'response',withCredentials: true });
  }
  //Prescription
  savePrescriptionOld(prescription : Prescription){
    return this.http.post(environment.rooturl + AppConstants.CREATE_PRESCRIPTION_API_URL,prescription,{ observe: 'response',withCredentials: true });
  }

  savePrescription(prescription : Prescription){
    return this.http.post(environment.rooturl + AppConstants.CREATE_PRESCRIPTION_API_URL,prescription,{ observe: 'response',withCredentials: true });
  }

  getDoctorPrescriptionDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.DOCTOR_PRESCRIPTION_API_URL+ "?doctorId="+id,{ observe: 'response',withCredentials: true });
  }
  getPatientPrescriptionDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.PATIENT_PRESCRIPTION_API_URL+ "?patientId="+id,{ observe: 'response',withCredentials: true });
  }
  getAllPrescriptionDetails(id: String){
    return this.http.get(environment.rooturl + AppConstants.ALL_PRESCRIPTION_API_URL+ "?UserId="+id,{ observe: 'response',withCredentials: true });
  }
  updatePrescription(prescription : Prescription){
    return this.http.post(environment.rooturl + AppConstants.UPDATE_PRESCRIPTION_API_URL,prescription,{ observe: 'response',withCredentials: true });
  }


  //Register
  registerUser(user : Register_form){
    return this.http.post(environment.rooturl + AppConstants.REGISTER_API_URL,user,{ observe: 'response'});
  }

  changePasswordRequest(user : Register_form){
    return this.http.post(environment.rooturl + AppConstants.RESET_PASSWORD_REQUEST_API_URL,user,{ observe: 'response'});
  }
  changePassword(user : Register_form){
    return this.http.post(environment.rooturl + AppConstants.CHANGE_PASSWORD_API_URL,user,{ observe: 'response'});
  }

  updateUser(user : Register_form){
    return this.http.post(environment.rooturl + AppConstants.UPDATE_API_URL,user,{ observe: 'response',withCredentials: true });
  }
  updateUsersPatients(jsonPayload : any,id: String){
    return this.http.post(environment.rooturl + AppConstants.UPDATE_MY_PAIENTS_API_URL+ "?userId="+id,jsonPayload,{ observe: 'response',withCredentials: true });
  }

  //Files
  sendFile(file: File) {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name); // Append the file to the FormData object

    return this.http.post(environment.rooturl + AppConstants.UPLOAD_FILE_API_URL, formData, {
        observe: 'response',
        withCredentials: true,
        headers: { 'enctype': 'multipart/form-data' } // Set the correct content type
    });
}


getFile(file_name: string) {
  const fullUrl = `${environment.rooturl}${AppConstants.DOWNLOAD_FILE_API_URL}/${file_name}`;
  console.log('Requesting file from URL:', fullUrl); // Log the full URL

  return this.http.get(fullUrl, {
      observe: 'response',
      withCredentials: true,
      responseType: 'blob'
  });
}




}