import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArtista, Artista } from '../artista.model';
import { ArtistaService } from '../service/artista.service';

@Injectable({ providedIn: 'root' })
export class ArtistaRoutingResolveService implements Resolve<IArtista> {
  constructor(protected service: ArtistaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArtista> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((artista: HttpResponse<Artista>) => {
          if (artista.body) {
            return of(artista.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Artista());
  }
}
