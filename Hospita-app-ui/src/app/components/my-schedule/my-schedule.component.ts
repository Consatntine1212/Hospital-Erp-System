import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';
import { ScheduleEntry } from 'src/app/model/schedule-entry.model';

@Component({
  selector: 'app-my-schedule',
  templateUrl: './my-schedule.component.html',
  styleUrls: ['./my-schedule.component.css']
})

export class MyScheduleComponent implements OnInit {
  ScheduleArray: ScheduleEntry[][] = [];
  user = new User();
  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails') || '{}');
    if (this.user && this.user.id) {
      this.dashboardService.getMyScheduleDetails(this.user.id).subscribe(
        (responseData: any) => {
          // Log the raw response data for testing
          console.log('Raw response data:', responseData);

          // Initialize the 2D array
          this.initializeScheduleArray();

          // Map each schedule to the corresponding time slot
          responseData.body.forEach((schedule: any) => {
            const newSchedule = new ScheduleEntry();
            newSchedule.id = schedule.id;
            newSchedule.startTime = schedule.timeSlot.startTime;
            newSchedule.isAvailable = schedule.available;
            newSchedule.dayOfWeek = schedule.dayOfWeek;

            // Determine the index of the time slot
            const timeIndex = this.getTimeIndex(newSchedule.startTime);
            
            if (timeIndex !== -1) {
              // Add the schedule to the corresponding array in the 2D array
              this.ScheduleArray[timeIndex].push(newSchedule);
              console.log('Added to ScheduleArray at timeIndex:', timeIndex);
            } else {
              console.error('Invalid time index for startTime:', newSchedule.startTime);
            }
          });
          console.log('Final ScheduleArray:', this.ScheduleArray);
        },
        error => {
          console.error('Error fetching schedule details:', error);
        }
      );
    }
  }

  initializeScheduleArray(): void {
    // Initialize the ScheduleArray with empty arrays for each time slot
    for (let i = 0; i < 28; i++) {
      this.ScheduleArray.push([]);
    }
    console.log('Initialized ScheduleArray:', this.ScheduleArray);
  }

  getTimeIndex(startTime: string): number {
    // Define an array of available time slots
    const timeSlots = [
        "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00",
        "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00",
        "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00",
        "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00",
        "20:00:00", "20:30:00", "21:00:00", "21:30:00"
    ];
    
    // Find the index of the startTime in the timeSlots array
    return timeSlots.indexOf(startTime);
  }

  onCellClicked(cellData: any) {
    // Do something with the clicked cell data
    const foundEntry = this.findEntryById(cellData.id);
    if (foundEntry.isAvailable){
      foundEntry.isAvailable = false;
    }
    else{
      foundEntry.isAvailable = true;
    }
  }

  findEntryById(x: number): any {
    // Iterate through each inner array in ScheduleArray
    for (const innerArray of this.ScheduleArray) {
      // Use the find method to search for the entry with the specified ID within the inner array
      const foundEntry = innerArray.find(entry => entry.id === x);
      // If a matching entry is found, return it
      if (foundEntry) {
        return foundEntry;
      }
    }
    // Return null if no entry with the specified ID is found
    return null;
  }

  onUpdateClick(){
    let isDone: boolean = true;
    // Iterate through each inner array in ScheduleArray
    for (let i = 0; i < this.ScheduleArray.length; i++) {
      const innerArray = this.ScheduleArray[i];
      for (let j = 0; j < innerArray.length; j++) {
        const entry: ScheduleEntry = innerArray[j];
        // Do something with the current entry
        this.dashboardService.updateMyScheduleentry(this.user.id, entry).subscribe(
          responseData => {
            const savedResponse = responseData.ok;
            if(responseData.ok){
              //window.alert('Schedule Updated successfully');
            }
            else{
              isDone = false;
            }
          },
          error => {
            console.error('Error:', error);
          });
      }
    } 
    if(isDone)   {
      window.alert('Schedule updated successfully!'+isDone);
    }else{
      window.alert('Schedule failed to update!');
    }
  }  
  onTestClick(){
    window.alert('This is an alert message!');

    const result = window.confirm('Are you sure you want to proceed?');
    if (result) {
      // User clicked OK
    } else {
      // User clicked Cancel
    }

    const userInput = window.prompt('Please enter your name:', 'John Doe');
    if (userInput !== null) {
      // User clicked OK and entered some text
      console.log('User input:', userInput);
    } else {
      // User clicked Cancel
    }
  }
}