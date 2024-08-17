export class Register_form {
        public id: string | null;
        public password: string | null;
        public firstName: string | null;
        public lastName: string | null;
        public email: string | null;
        public mobileNumber: string | null;
        public role: string | null;
        public gender: string | null;
        public status: string | null;
        public iconFileName: string | null;
        public description: string | null;
        public doctorSpecialization: string | null;
        public officeNo: string | null;
        public myPatient: boolean | null;
  
      constructor(
        id?: string | null,
        password?: string | null,
        firstName?: string | null,
        lastName?: string | null,
        email?: string | null,
        mobileNumber?: string | null,
        role?: string | null,
        gender?: string | null,
        status?: string | null,
        iconFileName?: string | null,
        description?: string | null,
        doctorSpecialization?: string | null,
        officeNo?: string | null,
        myPatient?: boolean | null,
      ) {
        this.id = id ?? null;
        this.password = password ?? null;
        this.firstName = firstName ?? null;
        this.lastName = lastName ?? null;
        this.email = email ?? null;
        this.mobileNumber = mobileNumber ?? null;
        this.role = role ?? null;
        this.gender = gender ?? null;
        this.status = status ?? null;
        this.iconFileName = iconFileName ?? null;
        this.description = description ?? null;
        this.doctorSpecialization = doctorSpecialization ?? null;
        this.officeNo = officeNo ?? null;
        this.myPatient = myPatient ?? null;
      }
  }
  