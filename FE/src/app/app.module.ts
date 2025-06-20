import {LOCALE_ID, NgModule, isDevMode} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {ComponentsModule} from "./components/components.module";
import {StoreModule} from "@ngrx/store";
import {AppReducer} from "@store/app.reducer";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {EffectsModule} from "@ngrx/effects";
import {AppEffects} from "@store/app.effects";
import {APP_PROVIDERS} from "@constants/constants";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';



@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
   //Dice deprecato e ho messo provideHttpClient -> HttpClientModule,
    ComponentsModule,
    StoreModule.forRoot({['AppState']: AppReducer}),
    EffectsModule.forRoot([AppEffects]),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: !isDevMode() }),
  ],
  providers: [
    ...APP_PROVIDERS,
    provideHttpClient(withInterceptorsFromDi()),
    {provide: LOCALE_ID, useValue: 'it-IT'},
    provideAnimationsAsync(),
  ],
  exports: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
