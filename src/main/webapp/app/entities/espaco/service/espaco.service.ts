import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEspaco, getEspacoIdentifier } from '../espaco.model';

export type EntityResponseType = HttpResponse<IEspaco>;
export type EntityArrayResponseType = HttpResponse<IEspaco[]>;

@Injectable({ providedIn: 'root' })
export class EspacoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/espacos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(espaco: IEspaco): Observable<EntityResponseType> {
    return this.http.post<IEspaco>(this.resourceUrl, espaco, { observe: 'response' });
  }

  update(espaco: IEspaco): Observable<EntityResponseType> {
    return this.http.put<IEspaco>(`${this.resourceUrl}/${getEspacoIdentifier(espaco) as number}`, espaco, { observe: 'response' });
  }

  partialUpdate(espaco: IEspaco): Observable<EntityResponseType> {
    return this.http.patch<IEspaco>(`${this.resourceUrl}/${getEspacoIdentifier(espaco) as number}`, espaco, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEspaco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEspaco[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEspacoToCollectionIfMissing(espacoCollection: IEspaco[], ...espacosToCheck: (IEspaco | null | undefined)[]): IEspaco[] {
    const espacos: IEspaco[] = espacosToCheck.filter(isPresent);
    if (espacos.length > 0) {
      const espacoCollectionIdentifiers = espacoCollection.map(espacoItem => getEspacoIdentifier(espacoItem)!);
      const espacosToAdd = espacos.filter(espacoItem => {
        const espacoIdentifier = getEspacoIdentifier(espacoItem);
        if (espacoIdentifier == null || espacoCollectionIdentifiers.includes(espacoIdentifier)) {
          return false;
        }
        espacoCollectionIdentifiers.push(espacoIdentifier);
        return true;
      });
      return [...espacosToAdd, ...espacoCollection];
    }
    return espacoCollection;
  }
}
