import { Component, OnInit } from '@angular/core';
import { Contact } from "src/app/model/contact.model";
import { NgForm } from '@angular/forms';
import { getCookie } from 'typescript-cookie';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  model = new Contact();

  constructor(private dashboardService: DashboardService) {

  }

  ngOnInit() {
      
  }

  saveMessage(contactForm: NgForm) {
    this.model = contactForm.value; // Update model with form values
    console.log("Contact Model: ", this.model); // Log the contact model
    
    this.dashboardService.saveMessage(this.model).subscribe(
      responseData => {
        console.log("Response Data: ", responseData); // Log response from the backend
        this.model = <any>responseData.body; // Update model based on response
        contactForm.resetForm();
      },
      error => {
        console.error('Error saving message:', error); // Log any errors
      }
    );
  }
  

}
