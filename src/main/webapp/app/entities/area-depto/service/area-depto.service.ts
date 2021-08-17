import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaDepto, getAreaDeptoIdentifier } from '../area-depto.model';

export type EntityResponseType = HttpResponse<IAreaDepto>;
export type EntityArrayResponseType = HttpResponse<IAreaDepto[]>;

@Injectable({ providedIn: 'root' })
export class AreaDeptoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-deptos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(areaDepto: IAreaDepto): Observable<EntityResponseType> {
    return this.http.post<IAreaDepto>(this.resourceUrl, areaDepto, { observe: 'response' });
  }

  update(areaDepto: IAreaDepto): Observable<EntityResponseType> {
    return this.http.put<IAreaDepto>(`${this.resourceUrl}/${getAreaDeptoIdentifier(areaDepto) as number}`, areaDepto, {
      observe: 'response',
    });
  }

  partialUpdate(areaDepto: IAreaDepto): Observable<EntityResponseType> {
    return this.http.patch<IAreaDepto>(`${this.resourceUrl}/${getAreaDeptoIdentifier(areaDepto) as number}`, areaDepto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaDepto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaDepto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAreaDeptoToCollectionIfMissing(
    areaDeptoCollection: IAreaDepto[],
    ...areaDeptosToCheck: (IAreaDepto | null | undefined)[]
  ): IAreaDepto[] {
    const areaDeptos: IAreaDepto[] = areaDeptosToCheck.filter(isPresent);
    if (areaDeptos.length > 0) {
      const areaDeptoCollectionIdentifiers = areaDeptoCollection.map(areaDeptoItem => getAreaDeptoIdentifier(areaDeptoItem)!);
      const areaDeptosToAdd = areaDeptos.filter(areaDeptoItem => {
        const areaDeptoIdentifier = getAreaDeptoIdentifier(areaDeptoItem);
        if (areaDeptoIdentifier == null || areaDeptoCollectionIdentifiers.includes(areaDeptoIdentifier)) {
          return false;
        }
        areaDeptoCollectionIdentifiers.push(areaDeptoIdentifier);
        return true;
      });
      return [...areaDeptosToAdd, ...areaDeptoCollection];
    }
    return areaDeptoCollection;
  }
}
