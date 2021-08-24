import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMoeda, getMoedaIdentifier } from '../moeda.model';

export type EntityResponseType = HttpResponse<IMoeda>;
export type EntityArrayResponseType = HttpResponse<IMoeda[]>;

@Injectable({ providedIn: 'root' })
export class MoedaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/moedas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moeda: IMoeda): Observable<EntityResponseType> {
    return this.http.post<IMoeda>(this.resourceUrl, moeda, { observe: 'response' });
  }

  update(moeda: IMoeda): Observable<EntityResponseType> {
    return this.http.put<IMoeda>(`${this.resourceUrl}/${getMoedaIdentifier(moeda) as number}`, moeda, { observe: 'response' });
  }

  partialUpdate(moeda: IMoeda): Observable<EntityResponseType> {
    return this.http.patch<IMoeda>(`${this.resourceUrl}/${getMoedaIdentifier(moeda) as number}`, moeda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMoeda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMoeda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMoedaToCollectionIfMissing(moedaCollection: IMoeda[], ...moedasToCheck: (IMoeda | null | undefined)[]): IMoeda[] {
    const moedas: IMoeda[] = moedasToCheck.filter(isPresent);
    if (moedas.length > 0) {
      const moedaCollectionIdentifiers = moedaCollection.map(moedaItem => getMoedaIdentifier(moedaItem)!);
      const moedasToAdd = moedas.filter(moedaItem => {
        const moedaIdentifier = getMoedaIdentifier(moedaItem);
        if (moedaIdentifier == null || moedaCollectionIdentifiers.includes(moedaIdentifier)) {
          return false;
        }
        moedaCollectionIdentifiers.push(moedaIdentifier);
        return true;
      });
      return [...moedasToAdd, ...moedaCollection];
    }
    return moedaCollection;
  }
}
