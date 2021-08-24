import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISeguro, getSeguroIdentifier } from '../seguro.model';

export type EntityResponseType = HttpResponse<ISeguro>;
export type EntityArrayResponseType = HttpResponse<ISeguro[]>;

@Injectable({ providedIn: 'root' })
export class SeguroService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/seguros');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(seguro: ISeguro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seguro);
    return this.http
      .post<ISeguro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(seguro: ISeguro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seguro);
    return this.http
      .put<ISeguro>(`${this.resourceUrl}/${getSeguroIdentifier(seguro) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(seguro: ISeguro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seguro);
    return this.http
      .patch<ISeguro>(`${this.resourceUrl}/${getSeguroIdentifier(seguro) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISeguro>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISeguro[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSeguroToCollectionIfMissing(seguroCollection: ISeguro[], ...segurosToCheck: (ISeguro | null | undefined)[]): ISeguro[] {
    const seguros: ISeguro[] = segurosToCheck.filter(isPresent);
    if (seguros.length > 0) {
      const seguroCollectionIdentifiers = seguroCollection.map(seguroItem => getSeguroIdentifier(seguroItem)!);
      const segurosToAdd = seguros.filter(seguroItem => {
        const seguroIdentifier = getSeguroIdentifier(seguroItem);
        if (seguroIdentifier == null || seguroCollectionIdentifiers.includes(seguroIdentifier)) {
          return false;
        }
        seguroCollectionIdentifiers.push(seguroIdentifier);
        return true;
      });
      return [...segurosToAdd, ...seguroCollection];
    }
    return seguroCollection;
  }

  protected convertDateFromClient(seguro: ISeguro): ISeguro {
    return Object.assign({}, seguro, {
      dataInicio: seguro.dataInicio?.isValid() ? seguro.dataInicio.format(DATE_FORMAT) : undefined,
      dataVenc: seguro.dataVenc?.isValid() ? seguro.dataVenc.format(DATE_FORMAT) : undefined,
      dataAtualSeguro: seguro.dataAtualSeguro?.isValid() ? seguro.dataAtualSeguro.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicio = res.body.dataInicio ? dayjs(res.body.dataInicio) : undefined;
      res.body.dataVenc = res.body.dataVenc ? dayjs(res.body.dataVenc) : undefined;
      res.body.dataAtualSeguro = res.body.dataAtualSeguro ? dayjs(res.body.dataAtualSeguro) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((seguro: ISeguro) => {
        seguro.dataInicio = seguro.dataInicio ? dayjs(seguro.dataInicio) : undefined;
        seguro.dataVenc = seguro.dataVenc ? dayjs(seguro.dataVenc) : undefined;
        seguro.dataAtualSeguro = seguro.dataAtualSeguro ? dayjs(seguro.dataAtualSeguro) : undefined;
      });
    }
    return res;
  }
}
