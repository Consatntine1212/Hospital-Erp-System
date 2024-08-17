import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClientXsrfModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { ContactComponent } from './components/contact/contact.component';
import { LoginComponent } from './components/Register-Login/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LogoutComponent } from './components/Register-Login/logout/logout.component';
import { NoticesComponent } from './components/notices/notices.component';
import { AccountComponent } from './components/account/account.component';
import { XhrInterceptor } from './interceptors/app.request.interceptor';
import { AuthActivateRouteGuard } from './routeguards/auth.routeguard';
import { HomeComponent } from './components/home/home.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
//foreing imports
import { AngularEditorModule } from '@kolkov/angular-editor';
import { CreateAppointmentComponent } from './components/create-appointment/create-appointment.component';
import { RegisterComponent } from './components/Register-Login/register/register.component';
import { DrugsListComponent } from './components/drugs-list/drugs-list.component';
import { AvailableDrugsListComponent } from './components/available-drugs-list/available-drugs-list.component';
import { PatientListComponent } from './components/prescriptions/patient-list/patient-list.component';
import { PrescriptionComponent } from './components/prescriptions/prescription/prescription.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ViewPrescriptionComponent } from './components/prescriptions/view-prescription/view-prescription.component';
import { MyScheduleComponent } from './components/my-schedule/my-schedule.component';
import { SearchForAppointmentsComponent } from './components/search-for-appointments/search-for-appointments.component';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { PrescriptionSearchComponent } from './components/prescriptions/prescription-search/prescription-search.component';
import { NewRegistrationPageComponent } from './components/Register-Login/new-registration-page/new-registration-page.component';
import { ResetPasswordComponent } from './components/Register-Login/reset-password/reset-password.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { DndDirective } from './directives/dnd/dnd.directive';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ContactComponent,
    LoginComponent,
    DashboardComponent,
    LogoutComponent,
    NoticesComponent,
    AccountComponent,
    HomeComponent,
    AppointmentsComponent,
    CreateAppointmentComponent,
    RegisterComponent,
    DrugsListComponent,
    AvailableDrugsListComponent,
    PatientListComponent,
    PrescriptionComponent,
    ViewPrescriptionComponent,
    MyScheduleComponent,
    SearchForAppointmentsComponent,
    ProfilePageComponent,
    PrescriptionSearchComponent,
    NewRegistrationPageComponent,
    ResetPasswordComponent,
    ChangePasswordComponent,
    DndDirective
  ],
  imports: [
    BrowserModule,
    AngularEditorModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxDatatableModule,
    //MatDialogModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),
  ],
  providers: [
    {
      provide : HTTP_INTERCEPTORS,
      useClass : XhrInterceptor,
      multi : true
    },AuthActivateRouteGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
