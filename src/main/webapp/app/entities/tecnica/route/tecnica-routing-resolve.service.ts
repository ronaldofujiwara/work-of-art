import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITecnica, Tecnica } from '../tecnica.model';
import { TecnicaService } from '../service/tecnica.service';

@Injectable({ providedIn: 'root' })
export class TecnicaRoutingResolveService implements Resolve<ITecnica> {
  constructor(protected service: TecnicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITecnica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tecnica: HttpResponse<Tecnica>) => {
          if (tecnica.body) {
            return of(tecnica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tecnica());
  }
}
