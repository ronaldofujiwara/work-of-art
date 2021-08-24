import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArtista, getArtistaIdentifier } from '../artista.model';

export type EntityResponseType = HttpResponse<IArtista>;
export type EntityArrayResponseType = HttpResponse<IArtista[]>;

@Injectable({ providedIn: 'root' })
export class ArtistaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/artistas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(artista: IArtista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(artista);
    return this.http
      .post<IArtista>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(artista: IArtista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(artista);
    return this.http
      .put<IArtista>(`${this.resourceUrl}/${getArtistaIdentifier(artista) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(artista: IArtista): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(artista);
    return this.http
      .patch<IArtista>(`${this.resourceUrl}/${getArtistaIdentifier(artista) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArtista>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArtista[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addArtistaToCollectionIfMissing(artistaCollection: IArtista[], ...artistasToCheck: (IArtista | null | undefined)[]): IArtista[] {
    const artistas: IArtista[] = artistasToCheck.filter(isPresent);
    if (artistas.length > 0) {
      const artistaCollectionIdentifiers = artistaCollection.map(artistaItem => getArtistaIdentifier(artistaItem)!);
      const artistasToAdd = artistas.filter(artistaItem => {
        const artistaIdentifier = getArtistaIdentifier(artistaItem);
        if (artistaIdentifier == null || artistaCollectionIdentifiers.includes(artistaIdentifier)) {
          return false;
        }
        artistaCollectionIdentifiers.push(artistaIdentifier);
        return true;
      });
      return [...artistasToAdd, ...artistaCollection];
    }
    return artistaCollection;
  }

  protected convertDateFromClient(artista: IArtista): IArtista {
    return Object.assign({}, artista, {
      dataAtualBio: artista.dataAtualBio?.isValid() ? artista.dataAtualBio.format(DATE_FORMAT) : undefined,
      dataAtualVerb: artista.dataAtualVerb?.isValid() ? artista.dataAtualVerb.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAtualBio = res.body.dataAtualBio ? dayjs(res.body.dataAtualBio) : undefined;
      res.body.dataAtualVerb = res.body.dataAtualVerb ? dayjs(res.body.dataAtualVerb) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((artista: IArtista) => {
        artista.dataAtualBio = artista.dataAtualBio ? dayjs(artista.dataAtualBio) : undefined;
        artista.dataAtualVerb = artista.dataAtualVerb ? dayjs(artista.dataAtualVerb) : undefined;
      });
    }
    return res;
  }
}
