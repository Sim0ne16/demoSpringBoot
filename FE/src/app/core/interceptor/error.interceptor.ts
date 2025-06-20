import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Store} from '@ngrx/store';
import {Router} from '@angular/router';
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(private store: Store, private router: Router) {
    }

    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(
            tap({
                next: () => null,
                error: (errorResponse: HttpErrorResponse) => {

                    let errorMessage = '';

                    if (errorResponse.error) {

                        // Errore del client
                        if (errorResponse.error.errorMessage) {
                            errorMessage = `Errore: ${errorResponse.error.errorMessage}`;
                        } else if (errorResponse.error.esito) {
                            errorMessage = `Errore: ${errorResponse.error.esito}`;
                        } else if (typeof errorResponse.error.error === 'string') {
                            errorMessage = `Errore: ${errorResponse.error.error}`;
                        } else {
                            errorMessage = `Errore: Errore di comunicazione con il server`;
                        }

                    } else {
                        // Errore del server
                        errorMessage = `Codice errore: ${errorResponse.status}\nMessaggio: ${
                            errorResponse.message || 'Errore di comunicazione con il server'
                        }`;
                    }


                    if (errorResponse.status == 403) {
                        //this.router.navigateByUrl('/login');

                    }

                    if (errorResponse.status != 404 && errorResponse.status != 200 && errorResponse.status != 403) {

                    }
                }
            })
        );
    }
}



