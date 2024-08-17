export class Prescription {
    public prescriptionId: number | null;
    public drugIds: number[];
    public prescriptionsSummary: string;
    public doctorId: string | null;
    public doctorName: string;
    public patientId: string | null;
    public patientName: string;
    public createDt: Date | null;
    public expirationDt: Date | null;
    public fulfillementDt: Date | null;
    public status: string;

    constructor(
        prescriptionId?: number | null,
        drugIds?: number[],
        prescriptionsSummary?: string,
        doctorId?: string | null,
        doctorName?: string,
        patientId?: string | null,
        patientName?: string,
        createDt?: Date | null,
        expirationDt?: Date | null,
        fulfillementDt?: Date | null,
        status?: string
    ) {
        this.prescriptionId = prescriptionId ?? null; // Use nullish coalescing operator to allow null
        this.drugIds = drugIds || [];
        this.prescriptionsSummary = prescriptionsSummary || '';
        this.doctorId = doctorId || '';
        this.doctorName = doctorName || '';
        this.patientId = patientId || '';
        this.patientName = patientName || '';
        this.createDt = createDt ?? null;
        this.expirationDt = expirationDt ?? null;
        this.fulfillementDt = fulfillementDt ?? null;
        this.status = status || 'Pending';
    }
}
