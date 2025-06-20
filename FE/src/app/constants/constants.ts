
import {APP_INITIALIZER, LOCALE_ID, Provider} from '@angular/core';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {AppState} from "@models/app.modal";
import {SpinnerInterceptor} from "../core/interceptor/spinner.interceptor";
import {ApiInterceptor} from "../core/interceptor/api.interceptor";
import {ErrorInterceptor} from "../core/interceptor/error.interceptor";
import {ConfigurationService} from "../core/services/configuration.service";
import {loadConfiguration} from "@utilities/functions/app.functions";


export const APP_PROVIDERS: Provider[] = [
    {
        provide: HTTP_INTERCEPTORS,
        useClass: SpinnerInterceptor,
        multi: true,
    },
    {
        provide: HTTP_INTERCEPTORS,
        useClass: ApiInterceptor,
        multi: true,
    },
    {
        provide: HTTP_INTERCEPTORS,
        useClass: ErrorInterceptor,
        multi: true,
    },
    {
        provide: LOCALE_ID,
        useValue: 'it-IT',
    },
    {
        provide: APP_INITIALIZER,
        useFactory: loadConfiguration,
        deps: [ConfigurationService],
        multi: true,
    },
];

export const INITIAL_APP_STATE: AppState = {
    loading: false,
};

export const CONFIG_URI = './assets/configuration/configuration.json';

