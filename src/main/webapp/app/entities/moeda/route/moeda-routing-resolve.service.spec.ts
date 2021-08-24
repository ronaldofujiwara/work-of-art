jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMoeda, Moeda } from '../moeda.model';
import { MoedaService } from '../service/moeda.service';

import { MoedaRoutingResolveService } from './moeda-routing-resolve.service';

describe('Service Tests', () => {
  describe('Moeda routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MoedaRoutingResolveService;
    let service: MoedaService;
    let resultMoeda: IMoeda | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MoedaRoutingResolveService);
      service = TestBed.inject(MoedaService);
      resultMoeda = undefined;
    });

    describe('resolve', () => {
      it('should return IMoeda returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMoeda = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMoeda).toEqual({ id: 123 });
      });

      it('should return new IMoeda if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMoeda = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMoeda).toEqual(new Moeda());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Moeda })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMoeda = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMoeda).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
