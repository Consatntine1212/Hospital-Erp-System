
export class Drugs {

    public drugId: number;
    public drugMarketName: string;
    public drugDesc: string;
    public deliveryMethod: string;
    public strength: string;
    public price: number;
    public activeIngredients: string;
    public manufacturerId: number;
    public manufacturer: string;
    public avelability: number;


    
    constructor(drugId?: number,drugMarketName?: string,drugDesc?: string,deliveryMethod?: string, strength?: string,
        price?: number,activeIngredients?: string, manufacturerId?: number, manufacturer?: string,avelability?: number){
          this.drugId = drugId || 0;
          this.drugMarketName = drugMarketName || "";
          this.drugDesc = drugDesc || "";
          this.deliveryMethod = deliveryMethod || "";
          this.strength = strength || "";
          this.price = price || 0;
          this.activeIngredients = activeIngredients || "";
          this.manufacturerId = manufacturerId || 0;
          this.manufacturer = manufacturer || "";
          this.avelability = avelability || 0;

    }
  
  }