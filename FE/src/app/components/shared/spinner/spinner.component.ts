import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {getSpinnerSelector} from '@store/app.selectors';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent implements OnInit {

  public loading$: Observable<boolean> = this.store.select(getSpinnerSelector);

  constructor(private store: Store) {
  }

  ngOnInit(): void {
  }

}
