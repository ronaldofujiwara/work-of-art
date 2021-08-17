jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAmbiente, Ambiente } from '../ambiente.model';
import { AmbienteService } from '../service/ambiente.service';

import { AmbienteRoutingResolveService } from './ambiente-routing-resolve.service';

describe('Service Tests', () => {
  describe('Ambiente routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AmbienteRoutingResolveService;
    let service: AmbienteService;
    let resultAmbiente: IAmbiente | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AmbienteRoutingResolveService);
      service = TestBed.inject(AmbienteService);
      resultAmbiente = undefined;
    });

    describe('resolve', () => {
      it('should return IAmbiente returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAmbiente = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAmbiente).toEqual({ id: 123 });
      });

      it('should return new IAmbiente if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAmbiente = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAmbiente).toEqual(new Ambiente());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Ambiente })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAmbiente = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAmbiente).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
