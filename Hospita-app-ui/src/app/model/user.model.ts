
export class User{

  public id: string;
  public firstName: string;
  public lastName: string;
  public mobileNumber: string;
  public email : string;
  public password: string;
  public role : string;
  public unredInbox : number;
  public statusCd: string;
  public statusMsg : string;
  public authStatus : string;


  constructor(id?: string,firstName?: string,lastName?: string, mobileNumber?: string, email?: string,  password?: string,role?: string, unredInbox?: number,
      statusCd?:string,statusMsg?:string, authStatus?:string){
        this.id = id || '';
        this.firstName = firstName || '';
        this.lastName = lastName || '';
        this.mobileNumber = mobileNumber || '';
        this.email = email || '';
        this.password = password || '';
        this.role = role || '';
        this.unredInbox = unredInbox || 0;
        this.statusCd = statusCd || '';
        this.statusMsg = statusMsg || '';
        this.authStatus = authStatus || '';
  }

}
