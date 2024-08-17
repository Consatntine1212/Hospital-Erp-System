
import { Component } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Register_form } from 'src/app/model/Register_form.model';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-new-registration-page',
  templateUrl: './new-registration-page.component.html',
  styleUrls: ['./new-registration-page.component.css']
})
export class NewRegistrationPageComponent {
  selectedTab: 'employee' | 'patient' = 'employee'; // Default to 'employee'
  errorMessage: string = '';
  user = new Register_form();
  constructor(private router: Router, private dashboardService: DashboardService) {}

  employee = {
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    gender: 'male',
    email: '',
    mobileNumber: '',
    role: ''
  };

  patient = {
    firstName: '',
    lastName: '',
    password: '',
    confirmPassword: '',
    gender: 'male',
    email: '',
    mobileNumber: '',
    role: 'Patient'
  };

  selectTab(tab: 'employee' | 'patient'): void {
    this.selectedTab = tab;
  }


  onSubmit(tab: 'employee' | 'patient'): void {
    if (tab === 'employee') {
        console.log('Employee Data:', this.employee);
        // Validate employee form
        if (!this.validateForm(this.employee)) {
            return;
        }
        if (!this.validatePassword(this.employee)) {
            return;
        }
        
        // Create a new instance of Register_form
        this.user = new Register_form();
        
        // Assign relevant properties from employee to this.user
        this.user.firstName = this.employee.firstName;
        this.user.lastName = this.employee.lastName;
        this.user.password = this.employee.password;
        this.user.email = this.employee.email;
        this.user.mobileNumber = this.employee.mobileNumber;
        this.user.role = this.employee.role;
        this.user.gender = this.employee.gender; // If applicable

        // Assign any default values for the missing properties
        this.user.id = null; // Or some default ID value
        this.user.status = null; // Set any necessary defaults
        this.user.iconFileName = null; // Set any necessary defaults
        this.user.description = null; // Set any necessary defaults

        this.register();
    } else if (tab === 'patient') {
        console.log('Hirer Data:', this.patient);
        // Validate patient form
        if (!this.validateForm(this.patient)) {
            return;
        }
        if (!this.validatePassword(this.patient)) {
            return;
        }

        // Create a new instance of Register_form
        this.user = new Register_form();
        
        // Assign relevant properties from patient to this.user
        this.user.firstName = this.patient.firstName;
        this.user.lastName = this.patient.lastName;
        this.user.password = this.patient.password;
        this.user.email = this.patient.email;
        this.user.mobileNumber = this.patient.mobileNumber;
        this.user.role = this.patient.role;
        this.user.gender = this.patient.gender; // If applicable

        // Assign any default values for the missing properties
        this.user.id = null; // Or some default ID value
        this.user.status = null; // Set any necessary defaults
        this.user.iconFileName = null; // Set any necessary defaults
        this.user.description = null; // Set any necessary defaults

        this.register();
    }
}

  // In your Angular component
  register() {
    // Perform your action here, such as submitting selected drugs to a service
    this.dashboardService.registerUser(this.user).subscribe(
        (responseData: any) => {
            if (responseData.body && responseData.body.message) {
                const message = responseData.body.message;
                console.log('responseData.body):', message);
                
                window.alert(message + ". Please check your inbox for your activation Email!");
                
                // If the message indicates success, navigate to another page
                if (message === 'User was successfully registered') {
                    this.router.navigate(['/home']); // Replace '/home' with the path to the desired page
                } else {
                    window.alert("User failed to register successfully!");
                }
            } else {
                window.alert("User failed to register successfully!");
                console.error('Invalid response data:', responseData);
            }
        },
        (error) => {
            // Handle error response here
            console.error('Registration error:', error);
            window.alert("An error occurred during registration: " + (error.error.message || 'Unknown error'));
        }
    ); 
}


  validateForm(user: any): boolean {
    if(user.firstName == ''){
      this.setErrorMessage('First name is required.');
      return false;
    }
    else if(user.lastName == ''){
      this.setErrorMessage('Last name is required.');
      return false;
    }
    else if(user.password == ''){
      this.setErrorMessage('Password field is required.');
      return false;
    }
    else if(user.confirmPassword == ''){
      this.setErrorMessage('Confirm password field  is required.');
      return false;
    }
    else if(user.confirmPassword !== user.password){
      this.setErrorMessage('Password and Confirm password must be the same.');
      return false;
    }
    else if(user.email == ''){
      this.setErrorMessage('Email field is required.');
      return false;
    }
    else if(user.phone == ''){
      this.setErrorMessage('Phone number is required.');
      return false;
    }
    else if(this.isValidPhoneNumber(user.phone)){
      this.setErrorMessage('First name is required.');
      return false;
    }
    else if(user.role == ''){
      this.setErrorMessage('Role is required.');
      return false;
    }
    this.setErrorMessage('');
    return true;
  }


  validatePassword(user: any): boolean {
    if (user.password !== user.confirmPassword) {
      this.setErrorMessage('Password must be equal to Confirm Password.');
      return false;
    }
    if (!this.hasRequiredLength(user.password)) {
      this.setErrorMessage('Password must be at least 8 characters long.');
      return false;
    }
    if (!this.hasUpperCase(user.password)) {
      this.setErrorMessage('Password must contain at least one uppercase letter.');
      return false;
    }
    if (!this.hasLowerCase(user.password)) {
      this.setErrorMessage('Password must contain at least one lowercase letter.');
      return false;
    }
    if (!this.hasNumber(user.password)) {
      this.setErrorMessage('Password must contain at least one number.');
      return false;
    }
    if (!this.hasSpecialCharacter(user.password)) {
      this.setErrorMessage('Password must contain at least one special character.');
      return false;
    }
    this.setErrorMessage('');
    return true;
  }

  hasRequiredLength(password: string): boolean {
    return password.length >= 8;
  }

  hasUpperCase(password: string): boolean {
    return /[A-Z]/.test(password);
  }

  hasLowerCase(password: string): boolean {
    return /[a-z]/.test(password);
  }

  hasNumber(password: string): boolean {
    return /[0-9]/.test(password);
  }

  hasSpecialCharacter(password: string): boolean {
    return /[!@#$%^&*(),.?":{}|<>]/.test(password);
  }

  setErrorMessage(message: string): void {
    this.errorMessage = message;
  }

  redirectToLogin(): void {
    window.location.href = '/login';
  }
  isValidPhoneNumber(phone: string): boolean {
    const phoneRegex = /^(\+\d{1,3})?\d{9}$/;
    return phoneRegex.test(phone);
}

}