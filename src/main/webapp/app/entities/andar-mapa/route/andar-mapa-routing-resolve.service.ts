import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAndarMapa, AndarMapa } from '../andar-mapa.model';
import { AndarMapaService } from '../service/andar-mapa.service';

@Injectable({ providedIn: 'root' })
export class AndarMapaRoutingResolveService implements Resolve<IAndarMapa> {
  constructor(protected service: AndarMapaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAndarMapa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((andarMapa: HttpResponse<AndarMapa>) => {
          if (andarMapa.body) {
            return of(andarMapa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AndarMapa());
  }
}
