import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuncaoArtista, getFuncaoArtistaIdentifier } from '../funcao-artista.model';

export type EntityResponseType = HttpResponse<IFuncaoArtista>;
export type EntityArrayResponseType = HttpResponse<IFuncaoArtista[]>;

@Injectable({ providedIn: 'root' })
export class FuncaoArtistaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/funcao-artistas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(funcaoArtista: IFuncaoArtista): Observable<EntityResponseType> {
    return this.http.post<IFuncaoArtista>(this.resourceUrl, funcaoArtista, { observe: 'response' });
  }

  update(funcaoArtista: IFuncaoArtista): Observable<EntityResponseType> {
    return this.http.put<IFuncaoArtista>(`${this.resourceUrl}/${getFuncaoArtistaIdentifier(funcaoArtista) as number}`, funcaoArtista, {
      observe: 'response',
    });
  }

  partialUpdate(funcaoArtista: IFuncaoArtista): Observable<EntityResponseType> {
    return this.http.patch<IFuncaoArtista>(`${this.resourceUrl}/${getFuncaoArtistaIdentifier(funcaoArtista) as number}`, funcaoArtista, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuncaoArtista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuncaoArtista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFuncaoArtistaToCollectionIfMissing(
    funcaoArtistaCollection: IFuncaoArtista[],
    ...funcaoArtistasToCheck: (IFuncaoArtista | null | undefined)[]
  ): IFuncaoArtista[] {
    const funcaoArtistas: IFuncaoArtista[] = funcaoArtistasToCheck.filter(isPresent);
    if (funcaoArtistas.length > 0) {
      const funcaoArtistaCollectionIdentifiers = funcaoArtistaCollection.map(
        funcaoArtistaItem => getFuncaoArtistaIdentifier(funcaoArtistaItem)!
      );
      const funcaoArtistasToAdd = funcaoArtistas.filter(funcaoArtistaItem => {
        const funcaoArtistaIdentifier = getFuncaoArtistaIdentifier(funcaoArtistaItem);
        if (funcaoArtistaIdentifier == null || funcaoArtistaCollectionIdentifiers.includes(funcaoArtistaIdentifier)) {
          return false;
        }
        funcaoArtistaCollectionIdentifiers.push(funcaoArtistaIdentifier);
        return true;
      });
      return [...funcaoArtistasToAdd, ...funcaoArtistaCollection];
    }
    return funcaoArtistaCollection;
  }
}
