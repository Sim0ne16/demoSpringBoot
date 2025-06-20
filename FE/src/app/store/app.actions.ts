import {createAction, props} from '@ngrx/store';

export const setSpinnerAction = createAction(
  '[SPINNER] set loading spinner',
  props<{ showSpinner: boolean }>()
);



