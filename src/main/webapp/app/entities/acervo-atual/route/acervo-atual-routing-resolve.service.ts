import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcervoAtual, AcervoAtual } from '../acervo-atual.model';
import { AcervoAtualService } from '../service/acervo-atual.service';

@Injectable({ providedIn: 'root' })
export class AcervoAtualRoutingResolveService implements Resolve<IAcervoAtual> {
  constructor(protected service: AcervoAtualService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcervoAtual> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((acervoAtual: HttpResponse<AcervoAtual>) => {
          if (acervoAtual.body) {
            return of(acervoAtual.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcervoAtual());
  }
}
