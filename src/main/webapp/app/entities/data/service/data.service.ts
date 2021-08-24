import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IData, getDataIdentifier } from '../data.model';

export type EntityResponseType = HttpResponse<IData>;
export type EntityArrayResponseType = HttpResponse<IData[]>;

@Injectable({ providedIn: 'root' })
export class DataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(data: IData): Observable<EntityResponseType> {
    return this.http.post<IData>(this.resourceUrl, data, { observe: 'response' });
  }

  update(data: IData): Observable<EntityResponseType> {
    return this.http.put<IData>(`${this.resourceUrl}/${getDataIdentifier(data) as number}`, data, { observe: 'response' });
  }

  partialUpdate(data: IData): Observable<EntityResponseType> {
    return this.http.patch<IData>(`${this.resourceUrl}/${getDataIdentifier(data) as number}`, data, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDataToCollectionIfMissing(dataCollection: IData[], ...dataToCheck: (IData | null | undefined)[]): IData[] {
    const data: IData[] = dataToCheck.filter(isPresent);
    if (data.length > 0) {
      const dataCollectionIdentifiers = dataCollection.map(dataItem => getDataIdentifier(dataItem)!);
      const dataToAdd = data.filter(dataItem => {
        const dataIdentifier = getDataIdentifier(dataItem);
        if (dataIdentifier == null || dataCollectionIdentifiers.includes(dataIdentifier)) {
          return false;
        }
        dataCollectionIdentifiers.push(dataIdentifier);
        return true;
      });
      return [...dataToAdd, ...dataCollection];
    }
    return dataCollection;
  }
}
