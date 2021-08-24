import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResponsavel, Responsavel } from '../responsavel.model';
import { ResponsavelService } from '../service/responsavel.service';

@Injectable({ providedIn: 'root' })
export class ResponsavelRoutingResolveService implements Resolve<IResponsavel> {
  constructor(protected service: ResponsavelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResponsavel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((responsavel: HttpResponse<Responsavel>) => {
          if (responsavel.body) {
            return of(responsavel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Responsavel());
  }
}
