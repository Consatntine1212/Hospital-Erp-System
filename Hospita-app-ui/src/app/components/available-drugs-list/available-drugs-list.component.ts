import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { Drugs } from 'src/app/model/drug.model';
import { User } from 'src/app/model/user.model';
import { Router } from '@angular/router';
import { Appointments } from 'src/app/model/appointments.model';
import { Time } from '@angular/common';

declare var $: any;


@Component({
  selector: 'app-available-drugs-list',
  templateUrl: './available-drugs-list.component.html',
  styleUrls: ['./available-drugs-list.component.css']
})
export class AvailableDrugsListComponent implements OnInit {

  title = 'angular-datatables';
  user = new User();
  drugsArray: Drugs[] = [];
  currOutstandingAmt: number = 0;

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

          // Detect changes and initialize DataTable
          this.cdr.detectChanges();
          this.initializeDataTable();
        }
      );
    }
  }

  ngAfterViewInit(): void {
    // Additional initialization if necessary
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

  onRowClicked(drug: Drugs): void {
    const result = window.confirm('Are you sure you want to select this drug?');
    if (result) {
      // User clicked OK
      // Handle row click
      console.log('Drug selected:', drug);
    } else {
      // User clicked Cancel
    }
  }
}
