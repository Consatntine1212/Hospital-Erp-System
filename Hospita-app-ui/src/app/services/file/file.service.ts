import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private baseUrl = 'http://localhost:8080/download';
  private uploadUrl = 'http://localhost:8080/upload';
  constructor(private http: HttpClient) { }

  downloadFile(filename: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${filename}`, { responseType: 'blob' });
  }
  uploadFile(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    const headers = new HttpHeaders({
      // Set any required headers here if needed
    });

    return this.http.post<string>(this.uploadUrl, formData, { headers });
  }
}
