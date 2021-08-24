import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResponsavel, getResponsavelIdentifier } from '../responsavel.model';

export type EntityResponseType = HttpResponse<IResponsavel>;
export type EntityArrayResponseType = HttpResponse<IResponsavel[]>;

@Injectable({ providedIn: 'root' })
export class ResponsavelService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/responsavels');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(responsavel: IResponsavel): Observable<EntityResponseType> {
    return this.http.post<IResponsavel>(this.resourceUrl, responsavel, { observe: 'response' });
  }

  update(responsavel: IResponsavel): Observable<EntityResponseType> {
    return this.http.put<IResponsavel>(`${this.resourceUrl}/${getResponsavelIdentifier(responsavel) as number}`, responsavel, {
      observe: 'response',
    });
  }

  partialUpdate(responsavel: IResponsavel): Observable<EntityResponseType> {
    return this.http.patch<IResponsavel>(`${this.resourceUrl}/${getResponsavelIdentifier(responsavel) as number}`, responsavel, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResponsavel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResponsavel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addResponsavelToCollectionIfMissing(
    responsavelCollection: IResponsavel[],
    ...responsavelsToCheck: (IResponsavel | null | undefined)[]
  ): IResponsavel[] {
    const responsavels: IResponsavel[] = responsavelsToCheck.filter(isPresent);
    if (responsavels.length > 0) {
      const responsavelCollectionIdentifiers = responsavelCollection.map(responsavelItem => getResponsavelIdentifier(responsavelItem)!);
      const responsavelsToAdd = responsavels.filter(responsavelItem => {
        const responsavelIdentifier = getResponsavelIdentifier(responsavelItem);
        if (responsavelIdentifier == null || responsavelCollectionIdentifiers.includes(responsavelIdentifier)) {
          return false;
        }
        responsavelCollectionIdentifiers.push(responsavelIdentifier);
        return true;
      });
      return [...responsavelsToAdd, ...responsavelCollection];
    }
    return responsavelCollection;
  }
}
