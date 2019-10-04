import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private apiEndpoint = 'http://localhost:8080/api1';

  constructor(private http: HttpClient) {
  }

  public get(url: string, endpoint?: string): Observable<object> {
    const subject: Subject<object> = new Subject();
    this.http.get<object>((endpoint ? endpoint : this.apiEndpoint) + url).subscribe((val: object) => {
      this.handleServerResponse(val);
      subject.next(val);
      subject.complete();
    }, error1 => {
      this.handleServerError(error1);
      subject.error(error1);
    });
    return subject;
  }

  public post(url: string, body: string, options: object): Observable<any> {
    const subject: Subject<object> = new Subject();
    let result;
    this.http.post(url, body, options).subscribe(value => {
      result = value;
    }, error1 => {
      this.handleServerError(error1);
      subject.error(error1);
    }, () => {
      subject.next(result);
      subject.complete();
    });
    return subject.asObservable();
  }

  //
  // ---> private
  //

  private handleServerResponse(result: any): void {
    if (!result) {
      console.error('server responded with null/undefinded');
    }
  }

  private handleServerError(error: any): void {
    const statusCode = error.status;
    if (statusCode === 503) {
      console.error('Server not available', 'Statuscode: ' + statusCode, JSON.stringify(error), null);
    } else {
      console.error('Server Error', 'statuscode=' + statusCode,
        JSON.stringify(error, undefined, 2), null);
    }
  }

}
