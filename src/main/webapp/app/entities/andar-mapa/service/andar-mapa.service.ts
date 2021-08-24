import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAndarMapa, getAndarMapaIdentifier } from '../andar-mapa.model';

export type EntityResponseType = HttpResponse<IAndarMapa>;
export type EntityArrayResponseType = HttpResponse<IAndarMapa[]>;

@Injectable({ providedIn: 'root' })
export class AndarMapaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/andar-mapas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(andarMapa: IAndarMapa): Observable<EntityResponseType> {
    return this.http.post<IAndarMapa>(this.resourceUrl, andarMapa, { observe: 'response' });
  }

  update(andarMapa: IAndarMapa): Observable<EntityResponseType> {
    return this.http.put<IAndarMapa>(`${this.resourceUrl}/${getAndarMapaIdentifier(andarMapa) as number}`, andarMapa, {
      observe: 'response',
    });
  }

  partialUpdate(andarMapa: IAndarMapa): Observable<EntityResponseType> {
    return this.http.patch<IAndarMapa>(`${this.resourceUrl}/${getAndarMapaIdentifier(andarMapa) as number}`, andarMapa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAndarMapa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAndarMapa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAndarMapaToCollectionIfMissing(
    andarMapaCollection: IAndarMapa[],
    ...andarMapasToCheck: (IAndarMapa | null | undefined)[]
  ): IAndarMapa[] {
    const andarMapas: IAndarMapa[] = andarMapasToCheck.filter(isPresent);
    if (andarMapas.length > 0) {
      const andarMapaCollectionIdentifiers = andarMapaCollection.map(andarMapaItem => getAndarMapaIdentifier(andarMapaItem)!);
      const andarMapasToAdd = andarMapas.filter(andarMapaItem => {
        const andarMapaIdentifier = getAndarMapaIdentifier(andarMapaItem);
        if (andarMapaIdentifier == null || andarMapaCollectionIdentifiers.includes(andarMapaIdentifier)) {
          return false;
        }
        andarMapaCollectionIdentifiers.push(andarMapaIdentifier);
        return true;
      });
      return [...andarMapasToAdd, ...andarMapaCollection];
    }
    return andarMapaCollection;
  }
}
