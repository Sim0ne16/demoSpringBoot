import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BooleanPipe } from './boolean.pipe';


const pipes = [
  BooleanPipe
];

@NgModule({
  declarations: [...pipes],
  imports: [CommonModule],
  exports: [...pipes],
})
export class PipesModule {
}
