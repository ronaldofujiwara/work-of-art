jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IArtista, Artista } from '../artista.model';
import { ArtistaService } from '../service/artista.service';

import { ArtistaRoutingResolveService } from './artista-routing-resolve.service';

describe('Service Tests', () => {
  describe('Artista routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ArtistaRoutingResolveService;
    let service: ArtistaService;
    let resultArtista: IArtista | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ArtistaRoutingResolveService);
      service = TestBed.inject(ArtistaService);
      resultArtista = undefined;
    });

    describe('resolve', () => {
      it('should return IArtista returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultArtista = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultArtista).toEqual({ id: 123 });
      });

      it('should return new IArtista if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultArtista = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultArtista).toEqual(new Artista());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Artista })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultArtista = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultArtista).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
