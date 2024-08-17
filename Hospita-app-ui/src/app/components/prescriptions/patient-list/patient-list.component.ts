import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from '../../../services/dashboard/dashboard.service';
import { User } from 'src/app/model/user.model';
import { Register_form } from 'src/app/model/Register_form.model';
import { Router } from '@angular/router'; // Import Router module
import { PatientDataService } from 'src/app/services/patient-data.service';
import { NumberValueAccessor } from '@angular/forms';
declare var $: any;

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit, AfterViewInit {
  title = 'angular-datatables';
  user = new User();
  patientsArray: Register_form[] = [];
  currOutstandingAmt: number = 0;
  patientIds: string[] = [];
  constructor(private dashboardService: DashboardService, private router: Router, private cdr: ChangeDetectorRef, private patientDataService: PatientDataService) { } 

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
    if (this.user && this.user.id) {
        this.dashboardService.getPatientDetails(this.user.id).subscribe(
            responseData => {
                console.log(responseData);
                this.patientsArray = <any>responseData.body;
                this.cdr.detectChanges();
                this.initializeDataTable();
            });
    }
}


  ngAfterViewInit(): void {
    // Initial DataTable initialization if data is already available (not likely in this case)
    if (this.patientsArray.length > 0) {
      this.initializeDataTable();
    }
  }

  initializeDataTable(): void {
    // Initialize DataTable
    var table = $('#example').DataTable({
      // Your DataTable initialization options
    });
  }

  onButtonClick(patient: Register_form, action: string): void {
    switch(action) {
      case 'view':
        this.viewPatient(patient);
        break;
      case 'prescribe':
        this.prescribePatient(patient);
        break;
      case 'delete':
        this.deletePatient(patient);
        break;
      case 'add':
        this.addPatient(patient);
        break;
      default:
        console.log('Unknown action:', action);
    }
  }

  viewPatient(patient: Register_form): void {
    // Logic to view patient details
    console.log('Viewing patient:', patient);
    this.patientDataService.changePatient(patient);
    this.router.navigate(['profile']);
  }

  prescribePatient(patient: Register_form): void {
    const confirmed = window.confirm(`Are you sure you want to prescribe this patient ${patient.firstName}?`);
    if (confirmed) {
      this.patientDataService.changePatient(patient);
      this.router.navigate(['Prescription/myPrescription']);
    }
  }

  deletePatient(patient: Register_form): void {
    const index = this.patientsArray.findIndex(p => p.id === patient.id);
    if (index !== -1) {
      this.patientsArray[index].myPatient = false;
      this.cdr.detectChanges(); // Manually trigger change detection
    } else {
      console.log('Patient not found:', patient);
    }
  }
  
  addPatient(patient: Register_form): void {
    const index = this.patientsArray.findIndex(p => p.id === patient.id);
    if (index !== -1) {
      this.patientsArray[index].myPatient = true;
      this.cdr.detectChanges(); // Manually trigger change detection
    } else {
      console.log('Patient not found:', patient);
    }
  }
  

  updateMyPatients(): void {

  }

  onUpdateClick(){
    this.patientIds = [];
    this.patientsArray.forEach(patient => {
      if(patient.myPatient){
        if(patient.id != null){
          this.patientIds.push(patient.id)
        }
      }
    });
    // Create the JSON object
    const jsonPayload = {
      patientIds: this.patientIds
    };


    this.dashboardService.updateUsersPatients(jsonPayload,this.user.id).subscribe(
      (responseData: any) => {
          // Check if the response is in the expected format
          if (responseData && responseData.body && responseData.body.message) {
              const message = responseData.body.message;
              console.log('Response message:', message);
              window.alert(message);
          } else {
              window.alert("Unexpected error!");
              console.error('Invalid response data:', responseData);
          }
      },
      (error) => {
          // Handle error response here
          console.error('Unexpected error', error);
          const errorMessage = error.error && error.error.message ? error.error.message : 'Unknown error';
          window.alert("An error occurred: " + errorMessage);
      }
  ); 
    // Now you can use jsonPayload as needed
    //console.log(JSON.stringify(jsonPayload)); // Convert it to a JSON string if needed
  }
}