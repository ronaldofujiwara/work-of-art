import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEspaco, Espaco } from '../espaco.model';
import { EspacoService } from '../service/espaco.service';

@Injectable({ providedIn: 'root' })
export class EspacoRoutingResolveService implements Resolve<IEspaco> {
  constructor(protected service: EspacoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEspaco> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((espaco: HttpResponse<Espaco>) => {
          if (espaco.body) {
            return of(espaco.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Espaco());
  }
}
