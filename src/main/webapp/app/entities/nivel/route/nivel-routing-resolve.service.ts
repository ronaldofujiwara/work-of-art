import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INivel, Nivel } from '../nivel.model';
import { NivelService } from '../service/nivel.service';

@Injectable({ providedIn: 'root' })
export class NivelRoutingResolveService implements Resolve<INivel> {
  constructor(protected service: NivelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INivel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nivel: HttpResponse<Nivel>) => {
          if (nivel.body) {
            return of(nivel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nivel());
  }
}
