import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginService } from 'src/app/services/login/login.service';
import { Router } from '@angular/router';
import { getCookie } from 'typescript-cookie';
import { RegisterService } from 'src/app/services/register/register.service';
import { Register_form } from 'src/app/model/Register_form.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  authStatus: string = "";
  model = new Register_form();


  constructor(private router: Router, private registerService: RegisterService) {}


  ngOnInit(): void {
  }

  registerUser(loginForm: NgForm) {/*
    // Log the form data to the console
    console.log('Form Data:', this.model);
    this.registerService.saveMessage(this.model).subscribe(
      responseData => {
        // Check if response status is 201
        if (responseData.status === 201) {
          // Reset the form
          loginForm.resetForm();
          // Redirect to the login page
          this.router.navigate(['/login']);
          console.log("we are in");
        } else {
          // Handle other status codes or errors if needed
          console.log("we are out");
        }
      },
      error => {
        if (error.status === 201) {
          // Reset the form
          loginForm.resetForm();
          // Redirect to the login page
          this.router.navigate(['/login']);
        } else {
          // Handle other errors
          console.error('Error:', error);
        }
      }
    );*/

  }

}
