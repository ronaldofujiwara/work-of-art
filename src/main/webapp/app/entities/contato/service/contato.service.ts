import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContato, getContatoIdentifier } from '../contato.model';

export type EntityResponseType = HttpResponse<IContato>;
export type EntityArrayResponseType = HttpResponse<IContato[]>;

@Injectable({ providedIn: 'root' })
export class ContatoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contatoes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(contato: IContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contato);
    return this.http
      .post<IContato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contato: IContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contato);
    return this.http
      .put<IContato>(`${this.resourceUrl}/${getContatoIdentifier(contato) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(contato: IContato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contato);
    return this.http
      .patch<IContato>(`${this.resourceUrl}/${getContatoIdentifier(contato) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addContatoToCollectionIfMissing(contatoCollection: IContato[], ...contatoesToCheck: (IContato | null | undefined)[]): IContato[] {
    const contatoes: IContato[] = contatoesToCheck.filter(isPresent);
    if (contatoes.length > 0) {
      const contatoCollectionIdentifiers = contatoCollection.map(contatoItem => getContatoIdentifier(contatoItem)!);
      const contatoesToAdd = contatoes.filter(contatoItem => {
        const contatoIdentifier = getContatoIdentifier(contatoItem);
        if (contatoIdentifier == null || contatoCollectionIdentifiers.includes(contatoIdentifier)) {
          return false;
        }
        contatoCollectionIdentifiers.push(contatoIdentifier);
        return true;
      });
      return [...contatoesToAdd, ...contatoCollection];
    }
    return contatoCollection;
  }

  protected convertDateFromClient(contato: IContato): IContato {
    return Object.assign({}, contato, {
      dataAtualizacao: contato.dataAtualizacao?.isValid() ? contato.dataAtualizacao.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAtualizacao = res.body.dataAtualizacao ? dayjs(res.body.dataAtualizacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contato: IContato) => {
        contato.dataAtualizacao = contato.dataAtualizacao ? dayjs(contato.dataAtualizacao) : undefined;
      });
    }
    return res;
  }
}
