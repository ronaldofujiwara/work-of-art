import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaDepto, AreaDepto } from '../area-depto.model';
import { AreaDeptoService } from '../service/area-depto.service';

@Injectable({ providedIn: 'root' })
export class AreaDeptoRoutingResolveService implements Resolve<IAreaDepto> {
  constructor(protected service: AreaDeptoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAreaDepto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((areaDepto: HttpResponse<AreaDepto>) => {
          if (areaDepto.body) {
            return of(areaDepto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AreaDepto());
  }
}
