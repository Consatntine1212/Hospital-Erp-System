import { Component, OnInit } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Subscription } from 'rxjs';
import { PatientDataService } from 'src/app/services/patient-data.service';
import { Register_form } from 'src/app/model/Register_form.model';
import { User } from 'src/app/model/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  patient: Register_form | null = null;
  user: User;

  private patientSubscription: Subscription  = new Subscription(); // Initialize with a default value

  constructor(private dashboardService: DashboardService, private patientDataService: PatientDataService,private router: Router) {
    this.user = new User(); // Initialize user here
  }

  ngOnInit(): void {
    this.patientSubscription = this.patientDataService.currentPatient.subscribe(patient => {
      this.patient = patient;
      const userDetails = sessionStorage.getItem('userdetails');
      //window.alert('Profile page of patient '+patient?.name);
    });
  }

}
