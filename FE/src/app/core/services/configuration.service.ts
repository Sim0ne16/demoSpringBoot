import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {shareReplay} from 'rxjs/operators';
import {Configuration} from 'src/app/models/configuration.models';
import {CONFIG_URI} from 'src/app/constants/constants';


// see https://medium.com/angular-in-depth/handling-angular-environments-in-continuous-delivery-eeaee96f0aae
@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {

  private configuration?: Observable<Configuration>;

  constructor(private http: HttpClient) {
    this.configuration = this.load();
  }

  public load(): Observable<Configuration> {
    if (!this.configuration)
      this.configuration = this.http.get<Configuration>(`${CONFIG_URI}`).pipe(shareReplay(1));

    return this.configuration;
  }
}
