import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GetAllFoodService {

  constructor() { }

  async LALALALALALAL(){
    const url = "http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/food/getAllFood"
    try{
      const response = await fetch(url)
      const data = await response.json()
      console.log(data.result);
      
      return data.result
      
    }catch(ex){
      console.error("very no good" + ex);
      
    }
  }
}
