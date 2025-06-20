import {createReducer, on} from '@ngrx/store';
import {INITIAL_APP_STATE} from '@constants/constants';
import {
  setSpinnerAction,
} from './app.actions';
import {AppState} from "@models/app.modal";

declare var $: any;


export const AppReducer = createReducer<AppState>(
  INITIAL_APP_STATE,


  // GENERALE
  on(setSpinnerAction, (state, props) => ({
    ...state,
    loading: props.showSpinner,
  })),

)





