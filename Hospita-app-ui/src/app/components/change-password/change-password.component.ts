import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  uuid: string | null = null;
  model: any = {
    uuid: '',
    password: '',
    password2: ''
  };
  passwordsMatch: boolean = true;
  constructor(private dashboardService: DashboardService,private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    // Accessing the query parameters
    this.route.queryParams.subscribe(params => {
      this.uuid = params['uuid']; // Retrieve the uuid parameter
      console.log('UUID:', this.uuid);
      this.model.uuid = this.uuid;
    });
  }

  validateUser(form: NgForm) {
    // Check if passwords match
    if (this.model.password !== this.model.password2) {
        this.passwordsMatch = false;
        return;
    }
    this.passwordsMatch = true;

    // Log the model
    console.log('Form Submitted', this.model);

    // Check if the form is valid
    if (form.valid) {
        this.dashboardService.changePassword(this.model).subscribe(
            (responseData: any) => {
                // Check if the response is in the expected format
                if (responseData && responseData.body && responseData.body.message) {
                    const message = responseData.body.message;
                    console.log('Response message:', message);
                    window.alert(message);
                    
                    // If the message indicates success, navigate to another page
                    if (message === 'Password changed successfully') {
                        this.router.navigate(['/home']); // Replace '/home' with the path to the desired page
                    } else {
                        window.alert("We could not change your password");
                    }
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
    } else {
        // Handle invalid form case
        console.error("Form is invalid.");
    }
}


}
