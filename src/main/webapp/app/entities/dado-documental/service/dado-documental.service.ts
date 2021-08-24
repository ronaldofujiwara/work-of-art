import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDadoDocumental, getDadoDocumentalIdentifier } from '../dado-documental.model';

export type EntityResponseType = HttpResponse<IDadoDocumental>;
export type EntityArrayResponseType = HttpResponse<IDadoDocumental[]>;

@Injectable({ providedIn: 'root' })
export class DadoDocumentalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dado-documentals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dadoDocumental: IDadoDocumental): Observable<EntityResponseType> {
    return this.http.post<IDadoDocumental>(this.resourceUrl, dadoDocumental, { observe: 'response' });
  }

  update(dadoDocumental: IDadoDocumental): Observable<EntityResponseType> {
    return this.http.put<IDadoDocumental>(`${this.resourceUrl}/${getDadoDocumentalIdentifier(dadoDocumental) as number}`, dadoDocumental, {
      observe: 'response',
    });
  }

  partialUpdate(dadoDocumental: IDadoDocumental): Observable<EntityResponseType> {
    return this.http.patch<IDadoDocumental>(
      `${this.resourceUrl}/${getDadoDocumentalIdentifier(dadoDocumental) as number}`,
      dadoDocumental,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDadoDocumental>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDadoDocumental[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDadoDocumentalToCollectionIfMissing(
    dadoDocumentalCollection: IDadoDocumental[],
    ...dadoDocumentalsToCheck: (IDadoDocumental | null | undefined)[]
  ): IDadoDocumental[] {
    const dadoDocumentals: IDadoDocumental[] = dadoDocumentalsToCheck.filter(isPresent);
    if (dadoDocumentals.length > 0) {
      const dadoDocumentalCollectionIdentifiers = dadoDocumentalCollection.map(
        dadoDocumentalItem => getDadoDocumentalIdentifier(dadoDocumentalItem)!
      );
      const dadoDocumentalsToAdd = dadoDocumentals.filter(dadoDocumentalItem => {
        const dadoDocumentalIdentifier = getDadoDocumentalIdentifier(dadoDocumentalItem);
        if (dadoDocumentalIdentifier == null || dadoDocumentalCollectionIdentifiers.includes(dadoDocumentalIdentifier)) {
          return false;
        }
        dadoDocumentalCollectionIdentifiers.push(dadoDocumentalIdentifier);
        return true;
      });
      return [...dadoDocumentalsToAdd, ...dadoDocumentalCollection];
    }
    return dadoDocumentalCollection;
  }
}
