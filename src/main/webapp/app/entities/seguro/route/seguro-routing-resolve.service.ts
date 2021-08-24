import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISeguro, Seguro } from '../seguro.model';
import { SeguroService } from '../service/seguro.service';

@Injectable({ providedIn: 'root' })
export class SeguroRoutingResolveService implements Resolve<ISeguro> {
  constructor(protected service: SeguroService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeguro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((seguro: HttpResponse<Seguro>) => {
          if (seguro.body) {
            return of(seguro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Seguro());
  }
}
