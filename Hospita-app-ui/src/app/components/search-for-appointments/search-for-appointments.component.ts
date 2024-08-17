import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from '../../services/dashboard/dashboard.service';
import { User } from 'src/app/model/user.model';
import { Appointments } from 'src/app/model/appointments.model';
import { Drugs } from 'src/app/model/drug.model';
import { Time } from '@angular/common';

declare var $: any;

@Component({
  selector: 'app-search-for-appointments',
  templateUrl: './search-for-appointments.component.html',
  styleUrls: ['./search-for-appointments.component.css']
})
export class SearchForAppointmentsComponent implements OnInit, AfterViewInit {
  title = 'angular-datatables';
  user = new User();
  appointmentsArray: Appointments[] = [];
  currOutstandingAmt: number = 0;
  currentDate: string;
  futureDate: string;

  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef) {
    const current = new Date();
    const future = new Date();
    future.setMonth(future.getMonth() + 2);

    this.currentDate = current.toISOString().split('T')[0]; // Convert to string in YYYY-MM-DD format
    this.futureDate = future.toISOString().split('T')[0]; // Convert to string in YYYY-MM-DD format
  }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
    if (this.user && this.user.id) {
      this.dashboardService.getAppointmentsByCriteria()
        .subscribe(
          (responseData: any) => {
            console.log('Raw response data:', responseData);
            this.appointmentsArray = responseData.body.map((appointment: any) => {
              const newAppointment = new Appointments();
              newAppointment.appointmentsId = appointment.appointmentsId;
              newAppointment.appointmentsDate = appointment.appointmentsDate;
              newAppointment.appointmentsTime = appointment.startTime || null;
              newAppointment.appointmentsDay = appointment.dayOfWeek || null;
              newAppointment.doctorId = appointment.doctorId || null;
              newAppointment.doctorFirstName = appointment.doctorFirstName || '';
              newAppointment.doctorLastName = appointment.doctorLastName || '';
              newAppointment.doctorSpecialization = appointment.doctorSpecialization || ''; 
              newAppointment.patientId = appointment.patientId || null;              
              newAppointment.patientFirstName = appointment.patientFirstName || '';
              newAppointment.patientLastName = appointment.patientLastName || '';
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

  ngAfterViewInit(): void {
    // Initial DataTable initialization if data is already available (not likely in this case)
    if (this.appointmentsArray.length > 0) {
      this.initializeDataTable();
    }
  }

  initializeDataTable(): void {
    // Initialize the date pickers
    $('#min, #max').datepicker({
      dateFormat: 'yy-mm-dd'
    });

    // Custom filtering function which will search data in date range
    $.fn.dataTable.ext.search.push(
      (settings: any, data: any, dataIndex: any) => {
        var min = $('#min').datepicker("getDate");
        var max = $('#max').datepicker("getDate");
        var startDate = new Date(data[1]); // Adjust the index based on the position of the appointmentsDate column in your table

        if ((min == null && max == null) ||
          (min == null && startDate <= max) ||
          (min <= startDate && max == null) ||
          (min <= startDate && startDate <= max)) {
          return true;
        }
        return false;
      }
    );

    // Initialize DataTable
    var table = $('#example').DataTable({
      // Your DataTable initialization options
    });

    // Re-draw the table when the date range filter changes
    $('#min, #max').change(() => {
      table.draw();
    });
  }

  onRowClicked(appointment: any) {
    console.log(appointment);
    const result = window.confirm('Are you sure you want to book this appointment?');
    if (result) {
      appointment.patientId = this.user.id;
      appointment.appointmentsDescription = 'this is just a test';

      // User clicked OK
      this.dashboardService.updateAppointment(appointment).subscribe(
        (responseData: any) => {
          // Handle success response
          // Show success message
          window.alert('Appointment booked successfully!');
          window.location.reload();
        },
        (error: any) => {
          // Handle error response
          console.error('Error:', error);
          // Show error message if necessary
          // this.toastr.error('Error occurred while booking appointment', 'Error');
        }
      );
    } else {
      // User clicked Cancel
    }
  }
}
