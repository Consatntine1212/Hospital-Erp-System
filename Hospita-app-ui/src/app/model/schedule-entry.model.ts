
export class ScheduleEntry {
    public id: number;
    public dayOfWeek: string;
    public startTime: string;
    public isAvailable: boolean;

    
    constructor(id?: number,dayOfWeek?: string,startTime?: string,
      isAvailable?: boolean){
          this.id = id || 0;
          this.dayOfWeek = dayOfWeek || "";
          this.startTime = startTime || "";
          this.isAvailable = isAvailable || false;
    }
  }
  