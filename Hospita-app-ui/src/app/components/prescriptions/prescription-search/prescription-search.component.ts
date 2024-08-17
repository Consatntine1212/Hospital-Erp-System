


import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { User } from 'src/app/model/user.model';
import { Prescription } from 'src/app/model/prescriptions.model';
import { Router } from '@angular/router';

declare var $: any;


@Component({
  selector: 'app-prescription-search',
  templateUrl: './prescription-search.component.html',
  styleUrls: ['./prescription-search.component.css']
})
export class PrescriptionSearchComponent implements OnInit, AfterViewInit {
  title = 'angular-datatables';
  user = new User();
  prescriptionsArray: Prescription[] = [];
  pendingOnly: boolean = true;

  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef, private router: Router) {}
  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
    if (this.user && this.user.id) {
      this.dashboardService.getAllPrescriptionDetails(this.user.id).subscribe(
        (responseData: any) => {
          this.prescriptionsArray = responseData.body.map((prescription: any) => {
            const newPrescription = new Prescription();
            newPrescription.prescriptionId = prescription.prescriptionId;
            newPrescription.prescriptionsSummary = prescription.prescriptionsSummary;
            newPrescription.doctorId = prescription.doctor.id;
            newPrescription.doctorName = prescription.doctor.name;
            newPrescription.patientId = prescription.patient.id;
            newPrescription.patientName = prescription.patient.name;
            newPrescription.createDt = prescription.createDt;
            newPrescription.expirationDt = prescription.expirationDt;
            newPrescription.fulfillementDt = prescription.fulfillementDt;
            newPrescription.status = prescription.status;
            return newPrescription;
          });
          this.cdr.detectChanges();
          this.initializeDataTable();
        }
      );
    }
  }

  ngAfterViewInit(): void {
    if (this.prescriptionsArray.length > 0) {
      this.initializeDataTable();
    }
  }

  initializeDataTable(): void {
    $('#min, #max').datepicker({
      dateFormat: 'yy-mm-dd'
    });

    $.fn.dataTable.ext.search.push(
      (settings: any, data: any, dataIndex: any) => {
        var min = $('#min').datepicker("getDate");
        var max = $('#max').datepicker("getDate");
        var startDate = new Date(data[2]); // Use the index for the createDt column
        var status = data[4]; // Use the index for the status column

        if ((min == null && max == null) ||
          (min == null && startDate <= max) ||
          (min <= startDate && max == null) ||
          (min <= startDate && startDate <= max)) {
          if (this.pendingOnly && status !== 'Pending') {
            return false;
          }
          return true;
        }
        return false;
      }
    );

    var table = $('#example').DataTable({
      // Your DataTable initialization options
    });

    $('#min, #max').change(() => {
      table.draw();
    });
  }

  onPendingOnlyChange(event: any): void {
    this.pendingOnly = event.target.checked;
    $('#example').DataTable().draw();
  }

  onRowClicked(appointment: any) {
    const result = window.confirm('Are you sure you want to book this appointment?');
    if (result) {
      this.dashboardService.saveAppointmentByInfo(appointment.appointmentsId, this.user.id, "test").subscribe(
        (responseData: any) => {
          window.alert('Appointment booked successfully!');
          window.location.reload();
        },
        (error: any) => {
          console.error('Error:', error);
        }
      );
    }
  }

  onButtonClick(prescription: Prescription): void {
    const confirmed = window.confirm(`Are you sure you want to fulfil prescription with id ${prescription.prescriptionId}, for patient ${prescription.patientName}?`);
    if (confirmed) {
      prescription.status = 'Fulfilled';
      console.log('fulfil prescription:', prescription);
      // Perform your action here, such as submitting selected drugs to a service
      this.dashboardService.updatePrescription(prescription).subscribe(
        (responseData: any) => {
          if (responseData.body && responseData.body.message) {
            const message = responseData.body.message;
            console.log('responseData.body):', message);

            window.alert(message);

            // If the message indicates success, navigate to another page
            if (message === 'Prescription updated successfully') {
              this.router.navigate(['/dashboard']); // Replace '/other-page' with the path to the desired page
            }
          } else {
            this.router.navigate(['/dashboard']); // Replace '/other-page' with the path to the desired page
            console.error('Invalid response data:', responseData);
            // Handle the case where responseData.body or responseData.body.message is missing
          }
        });

      this.dashboardService.updatePrescription
      // Add deletion logic here
    }
  }
}