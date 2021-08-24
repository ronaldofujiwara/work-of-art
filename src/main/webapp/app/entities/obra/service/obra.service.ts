import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObra, getObraIdentifier } from '../obra.model';

export type EntityResponseType = HttpResponse<IObra>;
export type EntityArrayResponseType = HttpResponse<IObra[]>;

@Injectable({ providedIn: 'root' })
export class ObraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/obras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(obra: IObra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obra);
    return this.http
      .post<IObra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(obra: IObra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obra);
    return this.http
      .put<IObra>(`${this.resourceUrl}/${getObraIdentifier(obra) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(obra: IObra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(obra);
    return this.http
      .patch<IObra>(`${this.resourceUrl}/${getObraIdentifier(obra) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IObra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IObra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addObraToCollectionIfMissing(obraCollection: IObra[], ...obrasToCheck: (IObra | null | undefined)[]): IObra[] {
    const obras: IObra[] = obrasToCheck.filter(isPresent);
    if (obras.length > 0) {
      const obraCollectionIdentifiers = obraCollection.map(obraItem => getObraIdentifier(obraItem)!);
      const obrasToAdd = obras.filter(obraItem => {
        const obraIdentifier = getObraIdentifier(obraItem);
        if (obraIdentifier == null || obraCollectionIdentifiers.includes(obraIdentifier)) {
          return false;
        }
        obraCollectionIdentifiers.push(obraIdentifier);
        return true;
      });
      return [...obrasToAdd, ...obraCollection];
    }
    return obraCollection;
  }

  protected convertDateFromClient(obra: IObra): IObra {
    return Object.assign({}, obra, {
      dataconversao: obra.dataconversao?.isValid() ? obra.dataconversao.format(DATE_FORMAT) : undefined,
      dataAlterApolice: obra.dataAlterApolice?.isValid() ? obra.dataAlterApolice.format(DATE_FORMAT) : undefined,
      dtVencFoto: obra.dtVencFoto?.isValid() ? obra.dtVencFoto.format(DATE_FORMAT) : undefined,
      dataInclusao: obra.dataInclusao?.isValid() ? obra.dataInclusao.format(DATE_FORMAT) : undefined,
      dataExclusao: obra.dataExclusao?.isValid() ? obra.dataExclusao.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataconversao = res.body.dataconversao ? dayjs(res.body.dataconversao) : undefined;
      res.body.dataAlterApolice = res.body.dataAlterApolice ? dayjs(res.body.dataAlterApolice) : undefined;
      res.body.dtVencFoto = res.body.dtVencFoto ? dayjs(res.body.dtVencFoto) : undefined;
      res.body.dataInclusao = res.body.dataInclusao ? dayjs(res.body.dataInclusao) : undefined;
      res.body.dataExclusao = res.body.dataExclusao ? dayjs(res.body.dataExclusao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((obra: IObra) => {
        obra.dataconversao = obra.dataconversao ? dayjs(obra.dataconversao) : undefined;
        obra.dataAlterApolice = obra.dataAlterApolice ? dayjs(obra.dataAlterApolice) : undefined;
        obra.dtVencFoto = obra.dtVencFoto ? dayjs(obra.dtVencFoto) : undefined;
        obra.dataInclusao = obra.dataInclusao ? dayjs(obra.dataInclusao) : undefined;
        obra.dataExclusao = obra.dataExclusao ? dayjs(obra.dataExclusao) : undefined;
      });
    }
    return res;
  }
}
