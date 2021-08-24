jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFuncaoArtista, FuncaoArtista } from '../funcao-artista.model';
import { FuncaoArtistaService } from '../service/funcao-artista.service';

import { FuncaoArtistaRoutingResolveService } from './funcao-artista-routing-resolve.service';

describe('Service Tests', () => {
  describe('FuncaoArtista routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FuncaoArtistaRoutingResolveService;
    let service: FuncaoArtistaService;
    let resultFuncaoArtista: IFuncaoArtista | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FuncaoArtistaRoutingResolveService);
      service = TestBed.inject(FuncaoArtistaService);
      resultFuncaoArtista = undefined;
    });

    describe('resolve', () => {
      it('should return IFuncaoArtista returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuncaoArtista = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFuncaoArtista).toEqual({ id: 123 });
      });

      it('should return new IFuncaoArtista if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuncaoArtista = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFuncaoArtista).toEqual(new FuncaoArtista());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FuncaoArtista })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFuncaoArtista = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFuncaoArtista).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
