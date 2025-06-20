import {createFeatureSelector, createSelector} from '@ngrx/store';
import {AppState} from "@models/app.modal";


const getState = createFeatureSelector<AppState>('AppState');


export const getSpinnerSelector = createSelector(
  getState,
  (state: AppState) => state.loading
);


