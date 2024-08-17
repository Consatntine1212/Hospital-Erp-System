import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { User } from 'src/app/model/user.model';
import { Appointments } from 'src/app/model/appointments.model';
import { Drugs } from 'src/app/model/drug.model';
import { Time } from '@angular/common';
import { Prescription } from 'src/app/model/prescriptions.model';
declare var $: any;
@Component({
  selector: 'app-view-prescription',
  templateUrl: './view-prescription.component.html',
  styleUrls: ['./view-prescription.component.css']
})
export class ViewPrescriptionComponent implements OnInit, AfterViewInit {
  title = 'angular-datatables';
  prescriptions = new Array();
  user = new User();
  prescriptionsArray: Prescription[] = [];
  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef) {}



  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
    if (this.user && this.user.id) {
      if (this.user.role == "Doctor"){
        this.dashboardService.getDoctorPrescriptionDetails(this.user.id).subscribe(
        (responseData: any) => {
          this.prescriptionsArray = responseData.body.map((prescription: any) => {
            const newPrescription = new Prescription();
            newPrescription.prescriptionId = prescription.prescriptionId;
            //newPrescription.drugId = prescription.drugId;
            newPrescription.prescriptionsSummary = prescription.prescriptionsSummary;
            newPrescription.doctorId = prescription.doctorId;
            newPrescription.patientId = prescription.patientId;
            newPrescription.createDt = prescription.createDt;
            newPrescription.expirationDt = prescription.expirationDt;
            newPrescription.fulfillementDt = prescription.fulfillementDt;
            newPrescription.status = prescription.status;
            return newPrescription;
          });
            // Detect changes and initialize DataTable
            this.cdr.detectChanges();
            this.initializeDataTable();
          }
        );
      }
      else if (this.user.role == "Patient"){
        this.dashboardService.getPatientPrescriptionDetails(this.user.id).subscribe(
          (responseData: any) => {
            this.prescriptionsArray = responseData.body.map((prescription: any) => {
              const newPrescription = new Prescription();
              newPrescription.prescriptionId = prescription.prescriptionId;
              //newPrescription.drugId = prescription.drugId;
              newPrescription.prescriptionsSummary = prescription.prescriptionsSummary;
              newPrescription.doctorId = prescription.doctorId;
              newPrescription.patientId = prescription.patientId;
              newPrescription.createDt = prescription.createDt;
              newPrescription.expirationDt = prescription.expirationDt;
              newPrescription.fulfillementDt = prescription.fulfillementDt;
              newPrescription.status = prescription.status;
              return newPrescription;
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
    // Initial DataTable initialization if data is already available (not likely in this case)
    if (this.prescriptionsArray.length > 0) {
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
    const result = window.confirm('Are you sure you want to book this appointment?');
    if (result) {
      // User clicked OK
      this.dashboardService.saveAppointmentByInfo(appointment.appointmentsId, this.user.id, "test").subscribe(
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

