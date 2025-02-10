import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegisterServiceService {

  constructor() { }

  registerUser(data: { username: string; email: string; password: string, image: string }): Promise<any> {
    const url = 'http://127.0.0.1:8080/CookBook-1.0-SNAPSHOT/webresources/user/registerUser';

    console.log("Sending POST request to:", url, "with data:", data);

    return fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
      .then((response) => {
        console.log("Response status:", response.status);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }
        return response.json()
      })
      .catch((error) => {
        console.error("Error in POST request:", error);
        throw error
      })
  }
}
