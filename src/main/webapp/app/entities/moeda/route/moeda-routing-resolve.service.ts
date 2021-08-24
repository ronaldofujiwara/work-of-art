import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMoeda, Moeda } from '../moeda.model';
import { MoedaService } from '../service/moeda.service';

@Injectable({ providedIn: 'root' })
export class MoedaRoutingResolveService implements Resolve<IMoeda> {
  constructor(protected service: MoedaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoeda> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moeda: HttpResponse<Moeda>) => {
          if (moeda.body) {
            return of(moeda.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Moeda());
  }
}
