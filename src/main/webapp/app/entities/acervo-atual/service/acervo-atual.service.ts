import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAcervoAtual, getAcervoAtualIdentifier } from '../acervo-atual.model';

export type EntityResponseType = HttpResponse<IAcervoAtual>;
export type EntityArrayResponseType = HttpResponse<IAcervoAtual[]>;

@Injectable({ providedIn: 'root' })
export class AcervoAtualService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/acervo-atuals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(acervoAtual: IAcervoAtual): Observable<EntityResponseType> {
    return this.http.post<IAcervoAtual>(this.resourceUrl, acervoAtual, { observe: 'response' });
  }

  update(acervoAtual: IAcervoAtual): Observable<EntityResponseType> {
    return this.http.put<IAcervoAtual>(`${this.resourceUrl}/${getAcervoAtualIdentifier(acervoAtual) as number}`, acervoAtual, {
      observe: 'response',
    });
  }

  partialUpdate(acervoAtual: IAcervoAtual): Observable<EntityResponseType> {
    return this.http.patch<IAcervoAtual>(`${this.resourceUrl}/${getAcervoAtualIdentifier(acervoAtual) as number}`, acervoAtual, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAcervoAtual>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcervoAtual[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAcervoAtualToCollectionIfMissing(
    acervoAtualCollection: IAcervoAtual[],
    ...acervoAtualsToCheck: (IAcervoAtual | null | undefined)[]
  ): IAcervoAtual[] {
    const acervoAtuals: IAcervoAtual[] = acervoAtualsToCheck.filter(isPresent);
    if (acervoAtuals.length > 0) {
      const acervoAtualCollectionIdentifiers = acervoAtualCollection.map(acervoAtualItem => getAcervoAtualIdentifier(acervoAtualItem)!);
      const acervoAtualsToAdd = acervoAtuals.filter(acervoAtualItem => {
        const acervoAtualIdentifier = getAcervoAtualIdentifier(acervoAtualItem);
        if (acervoAtualIdentifier == null || acervoAtualCollectionIdentifiers.includes(acervoAtualIdentifier)) {
          return false;
        }
        acervoAtualCollectionIdentifiers.push(acervoAtualIdentifier);
        return true;
      });
      return [...acervoAtualsToAdd, ...acervoAtualCollection];
    }
    return acervoAtualCollection;
  }
}
