import { Component, OnInit } from '@angular/core';
import { User } from "src/app/model/user.model";
import { Register_form } from 'src/app/model/Register_form.model';
import { NgForm } from '@angular/forms';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Router } from '@angular/router';
import { getCookie } from 'typescript-cookie';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  authStatus: string = "";
  model = new User();
  constructor(private dashboardService: DashboardService, private router: Router) {

  }

  ngOnInit(): void {
  }

  requestPasswordChange(loginForm: NgForm) {
    if (loginForm.valid) { // Check if the form is valid
        // Create a new instance of Register_form
        const registerForm = new Register_form();
        
        // Assign only the properties that are common between User and Register_form
        registerForm.id = this.model.id;
        registerForm.firstName = this.model.firstName;
        registerForm.lastName = this.model.lastName;
        registerForm.mobileNumber = this.model.mobileNumber;
        registerForm.email = this.model.email;
        registerForm.password = this.model.password; // Assuming password is also needed
        registerForm.role = this.model.role;

        // Call the changePasswordRequest with the new registerForm instance
        this.dashboardService.changePasswordRequest(registerForm).subscribe(
            (responseData: any) => {
                if (responseData.body && responseData.body.message) {
                    const message = responseData.body.message;
                    console.log('responseData.body):', message);
                    
                    window.alert(message + ". Please check your inbox for your Email!");
                    
                    // If the message indicates success, navigate to another page
                    if (message === 'Email was successfully send') {
                        this.router.navigate(['/home']); // Replace '/home' with the path to the desired page
                    } else {
                        window.alert("We could not find a user with that email");
                    }
                } else {
                    window.alert("Unexpected error!");
                    console.error('Invalid response data:', responseData);
                }
            },
            (error) => {
                // Handle error response here
                console.error('Unexpected error', error);
                window.alert("An error occurred " + (error.error.message || 'Unknown error'));
            }
        ); 
    } else {
        // Handle invalid form case
        console.error("Form is invalid.");
    }
}




  
}
