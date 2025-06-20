import {NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpinnerComponent } from '@shared/spinner/spinner.component';
import {PipesModule} from "@utilities/pipes/pipes.module";
import { HomeComponent } from '@features/home/home.component';


const components = [
  SpinnerComponent,
  HomeComponent
]


@NgModule({
  declarations: [...components ],
  imports: [
    CommonModule,
    PipesModule,
  ],
  exports: [...components, PipesModule],
})
export class ComponentsModule { }
