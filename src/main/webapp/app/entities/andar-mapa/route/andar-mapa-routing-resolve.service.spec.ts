jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAndarMapa, AndarMapa } from '../andar-mapa.model';
import { AndarMapaService } from '../service/andar-mapa.service';

import { AndarMapaRoutingResolveService } from './andar-mapa-routing-resolve.service';

describe('Service Tests', () => {
  describe('AndarMapa routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AndarMapaRoutingResolveService;
    let service: AndarMapaService;
    let resultAndarMapa: IAndarMapa | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AndarMapaRoutingResolveService);
      service = TestBed.inject(AndarMapaService);
      resultAndarMapa = undefined;
    });

    describe('resolve', () => {
      it('should return IAndarMapa returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAndarMapa = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAndarMapa).toEqual({ id: 123 });
      });

      it('should return new IAndarMapa if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAndarMapa = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAndarMapa).toEqual(new AndarMapa());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AndarMapa })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAndarMapa = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAndarMapa).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
