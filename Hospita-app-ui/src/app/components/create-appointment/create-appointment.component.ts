import { Component, OnInit } from '@angular/core';
import { Appointments } from 'src/app/model/appointments.model';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { NgForm } from '@angular/forms';
import { getCookie } from 'typescript-cookie';


@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css']
})
export class CreateAppointmentComponent implements OnInit {
  model = new Appointments();

  constructor(private dashboardService: DashboardService) {

  }

  ngOnInit() {
      
  }

  saveAppointment(contactForm: NgForm) {
    this.dashboardService.saveAppointment(this.model).subscribe(
      responseData => {
        this.model = <any> responseData.body;
        contactForm.resetForm();
      });

  }

}
