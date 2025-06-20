import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions} from '@ngrx/effects';
import {Store} from '@ngrx/store';
import {ApiService} from "../core/services/api.service";


@Injectable()
export class AppEffects {


  constructor(
    private store: Store,
    private actions$: Actions,
    private api: ApiService,
    private router: Router,
    //private auth: AuthService
  ) {
  }

}
