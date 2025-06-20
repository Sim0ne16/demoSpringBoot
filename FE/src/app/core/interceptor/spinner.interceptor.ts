import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable, throwError} from 'rxjs';
import {catchError, finalize} from 'rxjs/operators';
import {setSpinnerAction} from 'src/app/store/app.actions';


@Injectable()
export class SpinnerInterceptor implements HttpInterceptor {
    callCounter = 0;

    constructor(private store: Store) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.callCounter++;
        this.store.dispatch(setSpinnerAction({showSpinner: true}));

        return next.handle(req).pipe(
            catchError((error) => {
                this.store.dispatch(setSpinnerAction({showSpinner: false}));
                return throwError(() => error);
            }),

            finalize(() => {
                this.callCounter--;

                if (this.callCounter == 0)
                    this.store.dispatch(setSpinnerAction({showSpinner: false}));
            })
        )
    }
}
