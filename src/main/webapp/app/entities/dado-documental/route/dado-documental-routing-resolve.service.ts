import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDadoDocumental, DadoDocumental } from '../dado-documental.model';
import { DadoDocumentalService } from '../service/dado-documental.service';

@Injectable({ providedIn: 'root' })
export class DadoDocumentalRoutingResolveService implements Resolve<IDadoDocumental> {
  constructor(protected service: DadoDocumentalService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDadoDocumental> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dadoDocumental: HttpResponse<DadoDocumental>) => {
          if (dadoDocumental.body) {
            return of(dadoDocumental.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DadoDocumental());
  }
}
