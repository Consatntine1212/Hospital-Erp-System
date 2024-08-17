import { Time } from "@angular/common";

export class Appointments {

    public appointmentsId: number;
    public appointmentsDate: Date;
    public appointmentsTime: string;
    public appointmentsDay: string;
    public doctorId: string;
    public doctorFirstName: string;
    public doctorLastName: string;
    public doctorSpecialization: string;
    public patientId: string;
    public patientFirstName: string;
    public patientLastName: string;
    public appointmentsDescription: string;
    public createDt: Date;

    constructor(
        appointmentsId?: number,
        appointmentsDate?: Date,
        appointmentsTime?: string,
        appointmentsDay?: string,
        doctorId?: string,
        patientId?: string,
        patientFirstName?: string,
        patientLastName?: string,
        doctorFirstName?: string,
        doctorLastName?: string,
        doctorSpecialization?: string,
        appointmentsDescription?: string,
        createDt?: Date
    ){

        this.appointmentsId = appointmentsId || 0;
        this.appointmentsDate = appointmentsDate || new Date('1900-1-1');;
        this.appointmentsTime = appointmentsTime || '';
        this.appointmentsDay = appointmentsDay || '';
        this.doctorId = doctorId || '';
        this.doctorFirstName = doctorFirstName || '';
        this.doctorLastName = doctorLastName || '';
        this.doctorSpecialization = doctorSpecialization || '';
        this.patientId = patientId || '';
        this.patientFirstName = patientFirstName || '';
        this.patientLastName = patientLastName || '';
        this.appointmentsDescription = appointmentsDescription || '';
        this.createDt = new Date('1990-1-1');
    }
  
  }

