jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEspaco, Espaco } from '../espaco.model';
import { EspacoService } from '../service/espaco.service';

import { EspacoRoutingResolveService } from './espaco-routing-resolve.service';

describe('Service Tests', () => {
  describe('Espaco routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EspacoRoutingResolveService;
    let service: EspacoService;
    let resultEspaco: IEspaco | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EspacoRoutingResolveService);
      service = TestBed.inject(EspacoService);
      resultEspaco = undefined;
    });

    describe('resolve', () => {
      it('should return IEspaco returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaco = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEspaco).toEqual({ id: 123 });
      });

      it('should return new IEspaco if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaco = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEspaco).toEqual(new Espaco());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Espaco })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEspaco = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEspaco).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
