import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INivel, getNivelIdentifier } from '../nivel.model';

export type EntityResponseType = HttpResponse<INivel>;
export type EntityArrayResponseType = HttpResponse<INivel[]>;

@Injectable({ providedIn: 'root' })
export class NivelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nivels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nivel: INivel): Observable<EntityResponseType> {
    return this.http.post<INivel>(this.resourceUrl, nivel, { observe: 'response' });
  }

  update(nivel: INivel): Observable<EntityResponseType> {
    return this.http.put<INivel>(`${this.resourceUrl}/${getNivelIdentifier(nivel) as number}`, nivel, { observe: 'response' });
  }

  partialUpdate(nivel: INivel): Observable<EntityResponseType> {
    return this.http.patch<INivel>(`${this.resourceUrl}/${getNivelIdentifier(nivel) as number}`, nivel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNivelToCollectionIfMissing(nivelCollection: INivel[], ...nivelsToCheck: (INivel | null | undefined)[]): INivel[] {
    const nivels: INivel[] = nivelsToCheck.filter(isPresent);
    if (nivels.length > 0) {
      const nivelCollectionIdentifiers = nivelCollection.map(nivelItem => getNivelIdentifier(nivelItem)!);
      const nivelsToAdd = nivels.filter(nivelItem => {
        const nivelIdentifier = getNivelIdentifier(nivelItem);
        if (nivelIdentifier == null || nivelCollectionIdentifiers.includes(nivelIdentifier)) {
          return false;
        }
        nivelCollectionIdentifiers.push(nivelIdentifier);
        return true;
      });
      return [...nivelsToAdd, ...nivelCollection];
    }
    return nivelCollection;
  }
}
