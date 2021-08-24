import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuncaoArtista, FuncaoArtista } from '../funcao-artista.model';
import { FuncaoArtistaService } from '../service/funcao-artista.service';

@Injectable({ providedIn: 'root' })
export class FuncaoArtistaRoutingResolveService implements Resolve<IFuncaoArtista> {
  constructor(protected service: FuncaoArtistaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuncaoArtista> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((funcaoArtista: HttpResponse<FuncaoArtista>) => {
          if (funcaoArtista.body) {
            return of(funcaoArtista.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FuncaoArtista());
  }
}
