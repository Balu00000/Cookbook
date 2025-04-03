import { Injectable } from '@angular/core';
import SHA1 from 'sha.js';

@Injectable({
  providedIn: 'root'
})
export class HashService {

  hashString(input: string): string {
    return new SHA1('sha1').update(input).digest('hex');
  }
}