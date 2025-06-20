import {Component, OnInit} from '@angular/core';
import {setSpinnerAction} from "@store/app.actions";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  constructor( private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(setSpinnerAction({showSpinner: true}));


    setTimeout(()=>{
      this.store.dispatch(setSpinnerAction({showSpinner:false}))
    },2000)
  }





}
