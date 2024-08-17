import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Drugs } from 'src/app/model/drug.model';
import { User } from 'src/app/model/user.model';
import { Router } from '@angular/router';
import { Appointments } from 'src/app/model/appointments.model';
import { Time } from '@angular/common';
declare var $: any;



@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {
  user = new User();
  appointmentsArray: Appointments[] = [];
  currOutstandingAmt:number = 0;

  constructor(private dashboardService: DashboardService,private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {    
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
    if(this.user && this.user.id){
      if(this.user.role == "Doctor")
        {
        this.dashboardService.getDoctorAppointmentsDetails(this.user.id).subscribe(
          (responseData: any) => {
            console.log('Raw response data:', responseData);
            this.appointmentsArray = responseData.body.map((appointment: any) => {
              const newAppointment = new Appointments();
              newAppointment.appointmentsId = appointment.appointmentsId;
              newAppointment.appointmentsDate = appointment.appointmentsDate;
              newAppointment.appointmentsTime = appointment.schedule?.timeSlot?.startTime || null;
              newAppointment.appointmentsDay = appointment.schedule?.dayOfWeek || null;
              newAppointment.doctorId = appointment.doctor?.id || null;
              newAppointment.doctorFirstName = appointment.doctor?.firstName || '';
              newAppointment.doctorLastName = appointment.doctor?.lastName || '';
              newAppointment.patientId = appointment.patient?.id || null;
              newAppointment.patientFirstName = appointment.patient?.firstName || '';
              newAppointment.patientLastName = appointment.patient?.lastName || '';
              newAppointment.appointmentsDescription = appointment.appointmentsDescription;
              newAppointment.createDt = appointment.createDt;
              return newAppointment;
            });
          }
        );
      }
      else if (this.user.role == "Patient"){
        this.dashboardService.getPatientAppointmentsDetails(this.user.id).subscribe(
          (responseData: any) => {
            console.log('Raw response data:', responseData);
            this.appointmentsArray = responseData.body.map((appointment: any) => {
              const newAppointment = new Appointments();
              newAppointment.appointmentsId = appointment.appointmentsId;
              newAppointment.appointmentsDate = appointment.appointmentsDate;
              newAppointment.appointmentsTime = appointment.schedule?.timeSlot?.startTime || null;
              newAppointment.appointmentsDay = appointment.schedule?.dayOfWeek || null;
              newAppointment.doctorId = appointment.doctor?.id || null;
              newAppointment.doctorFirstName = appointment.doctor?.firstName || '';
              newAppointment.doctorLastName = appointment.doctor?.lastName || '';
              newAppointment.patientId = appointment.patient?.id || null;
              newAppointment.patientFirstName = appointment.patient?.firstName || '';
              newAppointment.patientLastName = appointment.patient?.lastName || '';
              newAppointment.appointmentsDescription = appointment.appointmentsDescription;
              newAppointment.createDt = appointment.createDt;
              return newAppointment;
            });
                      // Detect changes and initialize DataTable
          this.cdr.detectChanges();
          this.initializeDataTable();
          }
        );
      }
    }
  }  
  
  ngAfterViewInit(): void {
    // Additional initialization if necessary
  }

  initializeDataTable(): void {
    // Initialize DataTable
    const table = $('#example').DataTable({
      paging: true,
      searching: true,
      ordering: true,
      info: true,
      destroy: true, // This ensures that the table is re-initialized if it already exists
    });
  }

  onRowClicked(drug: Drugs): void {
    const result = window.confirm('Are you sure you want to select this drug?');
    if (result) {
      // User clicked OK
      // Handle row click
      console.log('Drug selected:', drug);
    } else {
      // User clicked Cancel
    }
  }
}

