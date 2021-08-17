import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAmbiente, getAmbienteIdentifier } from '../ambiente.model';

export type EntityResponseType = HttpResponse<IAmbiente>;
export type EntityArrayResponseType = HttpResponse<IAmbiente[]>;

@Injectable({ providedIn: 'root' })
export class AmbienteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ambientes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ambiente: IAmbiente): Observable<EntityResponseType> {
    return this.http.post<IAmbiente>(this.resourceUrl, ambiente, { observe: 'response' });
  }

  update(ambiente: IAmbiente): Observable<EntityResponseType> {
    return this.http.put<IAmbiente>(`${this.resourceUrl}/${getAmbienteIdentifier(ambiente) as number}`, ambiente, { observe: 'response' });
  }

  partialUpdate(ambiente: IAmbiente): Observable<EntityResponseType> {
    return this.http.patch<IAmbiente>(`${this.resourceUrl}/${getAmbienteIdentifier(ambiente) as number}`, ambiente, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAmbiente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmbiente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAmbienteToCollectionIfMissing(ambienteCollection: IAmbiente[], ...ambientesToCheck: (IAmbiente | null | undefined)[]): IAmbiente[] {
    const ambientes: IAmbiente[] = ambientesToCheck.filter(isPresent);
    if (ambientes.length > 0) {
      const ambienteCollectionIdentifiers = ambienteCollection.map(ambienteItem => getAmbienteIdentifier(ambienteItem)!);
      const ambientesToAdd = ambientes.filter(ambienteItem => {
        const ambienteIdentifier = getAmbienteIdentifier(ambienteItem);
        if (ambienteIdentifier == null || ambienteCollectionIdentifiers.includes(ambienteIdentifier)) {
          return false;
        }
        ambienteCollectionIdentifiers.push(ambienteIdentifier);
        return true;
      });
      return [...ambientesToAdd, ...ambienteCollection];
    }
    return ambienteCollection;
  }
}
