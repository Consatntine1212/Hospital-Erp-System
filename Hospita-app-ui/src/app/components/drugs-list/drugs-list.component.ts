import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Drugs } from 'src/app/model/drug.model';
import { User } from 'src/app/model/user.model';
declare var $: any;

@Component({
  selector: 'app-drugs-list',
  templateUrl: './drugs-list.component.html',
  styleUrls: ['./drugs-list.component.css']
})
export class DrugsListComponent implements OnInit {

  title = 'angular-datatables';
  user = new User();
  drugsArray: Drugs[] = [];
  selectedDrugs: Drugs[] = []; // Array to store selected drugs

  constructor(private dashboardService: DashboardService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || "{}");
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

          // Detect changes
          this.cdr.detectChanges();
          this.initializeDataTable();
        }
      );
    }
  }

  onRowClicked(drug: Drugs): void {
    // Handle row click if needed
  }

  initializeDataTable(): void {
    // Initialize DataTable
    const table = $('#example').DataTable({
      paging: true,
      searching: true,
      ordering: true,
      info: true,
      destroy: true, // This ensures that the table is re-initialized if it already exists
    });
  }

  onCheckboxChange(event: any, drug: Drugs): void {
    if (event.target.checked) {
      // If checkbox is checked, add drug to selectedDrugs array
      this.selectedDrugs.push(drug);
    } else {
      // If checkbox is unchecked, remove drug from selectedDrugs array
      const index = this.selectedDrugs.indexOf(drug);
      if (index !== -1) {
        this.selectedDrugs.splice(index, 1);
      }
    }
  }
  onConfirmClicked(): void {
    this.performActionOnSelectedDrugs();
  }
  // Method to perform an action on selected drugs
  performActionOnSelectedDrugs(): void {
    console.log('Selected drugs:', this.selectedDrugs);
    // Perform your action here, such as submitting selected drugs to a service
  }
}
