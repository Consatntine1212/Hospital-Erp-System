import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Drugs } from 'src/app/model/drug.model';
import { User } from 'src/app/model/user.model';
import { Register_form } from 'src/app/model/Register_form.model';
import { PatientDataService } from 'src/app/services/patient-data.service';
import { Subscription } from 'rxjs';
import { Prescription } from 'src/app/model/prescriptions.model';
import { Router } from '@angular/router';
declare var $: any;

@Component({
  selector: 'app-prescription',
  templateUrl: './prescription.component.html',
  styleUrls: ['./prescription.component.css']
})
export class PrescriptionComponent implements OnInit, OnDestroy {

  patient: Register_form | null = null;
  user: User;
  prescription: Prescription  = new Prescription();
  drugsArray: Drugs[] = [];
  selectedDrugs: Drugs[] = [];
  private patientSubscription: Subscription = new Subscription(); // Initialize with a default value

  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef, private patientDataService: PatientDataService,private router: Router) {
    this.user = new User(); // Initialize user here
  }

  ngOnInit(): void {
    this.patientSubscription = this.patientDataService.currentPatient.subscribe(patient => {
      this.patient = patient;
      const userDetails = sessionStorage.getItem('userdetails');
      if (userDetails) {
        this.user = JSON.parse(userDetails);
      }
      if (this.user && this.user.id) {
        this.dashboardService.getDrugsDetails().subscribe(
          (responseData: any) => {
            this.drugsArray = responseData.body.map((drug: any) => {
              const newDrug = new Drugs();
              newDrug.drugId = drug.drugId;
              newDrug.drugMarketName = drug.drugMarketName;
              newDrug.drugDesc = drug.drugDesc;
              newDrug.deliveryMethod = drug.deliveryMethod;
              newDrug.strength = drug.strength;
              newDrug.price = drug.price;
              newDrug.activeIngredients = drug.activeIngredients;
              newDrug.manufacturer = drug.manufacturer.manufacturerName;
              return newDrug;
            });

            this.cdr.detectChanges();
            this.initializeDataTable();
          }
        );
      }
    });
  }

  ngOnDestroy(): void {
    if (this.patientSubscription) {
      this.patientSubscription.unsubscribe();
    }
  }

  onRowClicked(drug: Drugs): void {
    // Handle row click if needed
  }

  initializeDataTable(): void {
    // Ensure DOM is ready before initializing DataTable
    $(() => {
      const table = $('#example').DataTable({
        paging: true,
        searching: true,
        ordering: true,
        info: true,
        destroy: true,
      });
    });
  }

  onCheckboxChange(event: any, drug: Drugs): void {
    if (event.target.checked) {
      this.selectedDrugs.push(drug);
    } else {
      const index = this.selectedDrugs.indexOf(drug);
      if (index !== -1) {
        this.selectedDrugs.splice(index, 1);
      }
    }
  }

  onConfirmClicked(): void {
    const userInput = window.prompt('Please enter the summary of your prescription:', 'Take one tablet daily');
    if (userInput !== null) {
      // User clicked OK and entered some text
      console.log('User input:', userInput);
      this.performActionOnSelectedDrugs(userInput);
    } else {
      // User clicked Cancel
    }
  }

  performActionOnSelectedDrugs( desc: string): void {
    console.log('Selected drugs:', this.selectedDrugs);
    const drugIds = this.selectedDrugs.map(drug => drug.drugId);
    this.prescription.doctorId = this.user.id;
    this.prescription.patientId = this.patient?.id ?? '';
    this.prescription.drugIds = drugIds;
    this.prescription.prescriptionsSummary = desc;

    // Perform your action here, such as submitting selected drugs to a service

    this.dashboardService.savePrescription(this.prescription).subscribe(
      (response: any) => {
        window.alert(response.body.message);
        console.log('response:', response);
        this.router.navigate(['/dashboard']);      },
      (error) => {
        console.error('error:', error);
      }
    );
  }
}
