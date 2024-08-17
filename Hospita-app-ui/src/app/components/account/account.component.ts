import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DashboardService } from '../../services/dashboard/dashboard.service';
import { User } from 'src/app/model/user.model';
import { Account } from 'src/app/model/account.model';
import { FileService } from 'src/app/services/file/file.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Register_form } from 'src/app/model/Register_form.model';
import { Router } from '@angular/router';
@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  user = new User();
  account = new Account();
  
  isEditing: boolean = false; // Toggle this to switch between modes
  profileImageUrl: any = 'path/to/default/image.jpg'; // Default profile image URL
  profileImageUrlOriginal: any = ''; 
  editingProfileImageUrl: string = 'path/to/editing/image.jpg'; // Editing profile image URL
  selectedFile: File | null = null; // Variable to store the selected file

  @ViewChild('fileInput') fileInput!: ElementRef; // Use definite assignment assertion

  constructor(private dashboardService: DashboardService, private fileService: FileService, private sanitizer: DomSanitizer,private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage.getItem('userdetails')!);

    if (this.user) {
      this.dashboardService.getAccountDetails(this.user.id).subscribe(
        responseData => {
          console.log('Server Response:', responseData);
          this.account = <any>responseData.body;
          console.log('Account Details:', this.account);

          // Fetch the icon file after getting account details
          this.loadProfileImage(this.account.iconFileName); // Replace with the actual filename
        },
        error => {
          console.error('Error fetching account details:', error);
        }
      );
    }
  }

  loadProfileImage(filename: string): void {
    this.fileService.downloadFile(filename).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      this.profileImageUrl = this.sanitizer.bypassSecurityTrustUrl(url);
      this.profileImageUrlOriginal = this.profileImageUrl;
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0]; // Store the selected file
      this.loadImage(this.selectedFile); // Load the image to display it
    }
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    (event.target as HTMLElement).classList.add('dragover'); // Add drag over effect
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    (event.target as HTMLElement).classList.remove('dragover'); // Remove drag over effect
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    const files = event.dataTransfer?.files;
    if (files && files[0]) {
      this.selectedFile = files[0]; // Store the dropped file
      this.loadImage(this.selectedFile); // Load the image to display it
    }
    (event.target as HTMLElement).classList.remove('dragover'); // Remove the dragover class
  }

  private loadImage(file: File): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.profileImageUrl = e.target.result; // Set the image URL
    };
    reader.readAsDataURL(file); // Read the file as a data URL
  }

  Edit() {
    this.isEditing = !this.isEditing;
  }

  async Save() {
    try {
      const fileName = await this.uploadFile(); // Get the file name from the upload
      await this.updateUserProfile(fileName); // Pass the file name to updateUserProfile
      this.loadProfileImage(fileName);
    } catch (error) {
      console.error('Error during save operation:', error);
    }
    this.isEditing = false;
  }
  
  private uploadFile(): Promise<string> {
    return new Promise((resolve, reject) => {
      if (this.selectedFile) {
        this.fileService.uploadFile(this.selectedFile).subscribe(
          (response: any) => {
            console.log('File uploaded successfully:', response.message, response.name);
            this.profileImageUrl = this.sanitizer.bypassSecurityTrustUrl(response.name);
            resolve(response.name); // Resolve with the file name
          },
          (error) => {
            console.error('Error uploading file:', error);
            reject(error);
          }
        );
      } else {
        console.error('No file selected for upload!');
        reject(new Error('No file selected'));
      }
    });
  }
  
  private updateUserProfile(fileName: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const registerform = new Register_form();
      registerform.id = this.user.id;
      registerform.firstName = this.user.firstName;
      registerform.lastName = this.user.lastName;
      registerform.email = this.user.email;
      registerform.mobileNumber = this.user.mobileNumber;
      registerform.role = this.user.role;
      registerform.iconFileName = fileName; // Use the uploaded file name
      registerform.description = this.account.description;
      registerform.doctorSpecialization = this.account.doctorSpecialization;
      registerform.officeNo = this.account.officeNo;
      console.log(fileName);
      this.dashboardService.updateUser(registerform).subscribe(
        (response: any) => {
          console.log('User profile updated successfully:', response);
          resolve();
        },
        (error) => {
          console.error('Error updating user profile:', error);
          reject(error);
        }
      );
    });
  }
  
  
  

  CancelEdit() {
    // Handle cancel edit action
    this.isEditing = false; // Or any other logic you need
  }

  onSubmit() {
    // Handle form submission
  }
}
