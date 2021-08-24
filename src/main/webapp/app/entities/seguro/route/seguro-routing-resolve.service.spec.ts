jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISeguro, Seguro } from '../seguro.model';
import { SeguroService } from '../service/seguro.service';

import { SeguroRoutingResolveService } from './seguro-routing-resolve.service';

describe('Service Tests', () => {
  describe('Seguro routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SeguroRoutingResolveService;
    let service: SeguroService;
    let resultSeguro: ISeguro | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SeguroRoutingResolveService);
      service = TestBed.inject(SeguroService);
      resultSeguro = undefined;
    });

    describe('resolve', () => {
      it('should return ISeguro returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSeguro = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSeguro).toEqual({ id: 123 });
      });

      it('should return new ISeguro if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSeguro = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSeguro).toEqual(new Seguro());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Seguro })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSeguro = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSeguro).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
