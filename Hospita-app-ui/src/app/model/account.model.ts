
export class Account {

  public id: number;
  public iconFileName: string;
  public description: string;
  public doctorSpecialization: string;
  public officeNo: string;

  constructor(
    id?: number,
    iconFileName?: string,
    description?: string,
    doctorSpecialization?: string,
    officeNo?: string,
    ){
        this.id = id || 0;
        this.iconFileName = iconFileName || '';
        this.description = description || '';
        this.doctorSpecialization = doctorSpecialization || '';
        this.officeNo = officeNo || '';
  }

}
