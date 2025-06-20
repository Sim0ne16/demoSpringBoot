import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'booleanPipe',
})
export class BooleanPipe implements PipeTransform {
  transform(items: any): string {
    if (!!items) return 'Si';
    else return 'No';
  }
}
