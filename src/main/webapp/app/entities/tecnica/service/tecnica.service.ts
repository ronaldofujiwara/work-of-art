import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITecnica, getTecnicaIdentifier } from '../tecnica.model';

export type EntityResponseType = HttpResponse<ITecnica>;
export type EntityArrayResponseType = HttpResponse<ITecnica[]>;

@Injectable({ providedIn: 'root' })
export class TecnicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tecnicas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tecnica: ITecnica): Observable<EntityResponseType> {
    return this.http.post<ITecnica>(this.resourceUrl, tecnica, { observe: 'response' });
  }

  update(tecnica: ITecnica): Observable<EntityResponseType> {
    return this.http.put<ITecnica>(`${this.resourceUrl}/${getTecnicaIdentifier(tecnica) as number}`, tecnica, { observe: 'response' });
  }

  partialUpdate(tecnica: ITecnica): Observable<EntityResponseType> {
    return this.http.patch<ITecnica>(`${this.resourceUrl}/${getTecnicaIdentifier(tecnica) as number}`, tecnica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITecnica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITecnica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTecnicaToCollectionIfMissing(tecnicaCollection: ITecnica[], ...tecnicasToCheck: (ITecnica | null | undefined)[]): ITecnica[] {
    const tecnicas: ITecnica[] = tecnicasToCheck.filter(isPresent);
    if (tecnicas.length > 0) {
      const tecnicaCollectionIdentifiers = tecnicaCollection.map(tecnicaItem => getTecnicaIdentifier(tecnicaItem)!);
      const tecnicasToAdd = tecnicas.filter(tecnicaItem => {
        const tecnicaIdentifier = getTecnicaIdentifier(tecnicaItem);
        if (tecnicaIdentifier == null || tecnicaCollectionIdentifiers.includes(tecnicaIdentifier)) {
          return false;
        }
        tecnicaCollectionIdentifiers.push(tecnicaIdentifier);
        return true;
      });
      return [...tecnicasToAdd, ...tecnicaCollection];
    }
    return tecnicaCollection;
  }
}
