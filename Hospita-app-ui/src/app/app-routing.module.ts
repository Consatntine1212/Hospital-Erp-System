import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactComponent } from './components/contact/contact.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AccountComponent } from './components/account/account.component';
import { NoticesComponent } from './components/notices/notices.component';
import { AuthActivateRouteGuard } from './routeguards/auth.routeguard';
import { HomeComponent } from './components/home/home.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
//Auth System
import { RegisterComponent } from './components/Register-Login/register/register.component';
import { LoginComponent } from './components/Register-Login/login/login.component';
import { LogoutComponent } from './components/Register-Login/logout/logout.component';
import { ResetPasswordComponent } from './components/Register-Login/reset-password/reset-password.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
//Apointments
import { CreateAppointmentComponent } from './components/create-appointment/create-appointment.component';
//drugs
import { DrugsListComponent } from './components/drugs-list/drugs-list.component';
import { AvailableDrugsListComponent } from './components/available-drugs-list/available-drugs-list.component';
//patient
import { PatientListComponent } from './components/prescriptions/patient-list/patient-list.component';
import { PrescriptionComponent } from './components/prescriptions/prescription/prescription.component';
import { ViewPrescriptionComponent } from './components/prescriptions/view-prescription/view-prescription.component';
import { MyScheduleComponent } from './components/my-schedule/my-schedule.component';
import { SearchForAppointmentsComponent } from './components/search-for-appointments/search-for-appointments.component';
//Profile
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
//Prescription
import { PrescriptionSearchComponent } from './components/prescriptions/prescription-search/prescription-search.component';


import { NewRegistrationPageComponent } from './components/Register-Login/new-registration-page/new-registration-page.component';
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'contact', component: ContactComponent},
  { path: 'notices', component: NoticesComponent},
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'logout', component: LogoutComponent},
  { path: 'myAccount', component: AccountComponent, canActivate: [AuthActivateRouteGuard]},
  { path: 'myAppointments', component: AppointmentsComponent, canActivate: [AuthActivateRouteGuard]},
  { path: 'newAppointments', component: CreateAppointmentComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'myDrugs', component: DrugsListComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'myAvailableDrugs', component: AvailableDrugsListComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'Prescription/myPatients', component: PatientListComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'Prescription/myPrescription', component: PrescriptionComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'Prescription/ViewMyPrescription', component: ViewPrescriptionComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'Prescription/SearchForPrescription', component: PrescriptionSearchComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'ViewMySchedule', component: MyScheduleComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'SearchForAppointments', component: SearchForAppointmentsComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'newRegister', component: NewRegistrationPageComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'resetpassword', component: ResetPasswordComponent, canActivate: [AuthActivateRouteGuard] },
  { path: 'changeMyPassword', component: ChangePasswordComponent, canActivate: [AuthActivateRouteGuard] },
];
NewRegistrationPageComponent
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
