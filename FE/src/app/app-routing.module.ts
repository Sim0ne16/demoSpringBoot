import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppRoutes} from "@constants/enums";
import {HomeComponent} from "@features/home/home.component";

const routes: Routes = [

    {
        path: AppRoutes.Home,
        component: HomeComponent,
        //Controllo per l'autenticazione, canActivate: [canActivate],
    },
    {
        path: '**',
        redirectTo: AppRoutes.Home,
        pathMatch: 'full'
    },
    {
        path: '',
        redirectTo: AppRoutes.Home,
        pathMatch: 'full'
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
