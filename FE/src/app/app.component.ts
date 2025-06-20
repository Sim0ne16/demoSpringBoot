import { Component } from '@angular/core';
import {ApiService} from "./core/services/api.service";
import {Store} from "@ngrx/store";
import {SessionStorageService} from "./core/services/session-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'school_demo';



  constructor(
      private store: Store,
      private apiService: ApiService,
      private sessionStorageService: SessionStorageService
  ) {
  }

}
