import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContato, Contato } from '../contato.model';
import { ContatoService } from '../service/contato.service';

@Injectable({ providedIn: 'root' })
export class ContatoRoutingResolveService implements Resolve<IContato> {
  constructor(protected service: ContatoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContato> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((contato: HttpResponse<Contato>) => {
          if (contato.body) {
            return of(contato.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contato());
  }
}
