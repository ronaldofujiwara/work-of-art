jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INivel, Nivel } from '../nivel.model';
import { NivelService } from '../service/nivel.service';

import { NivelRoutingResolveService } from './nivel-routing-resolve.service';

describe('Service Tests', () => {
  describe('Nivel routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NivelRoutingResolveService;
    let service: NivelService;
    let resultNivel: INivel | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NivelRoutingResolveService);
      service = TestBed.inject(NivelService);
      resultNivel = undefined;
    });

    describe('resolve', () => {
      it('should return INivel returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNivel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNivel).toEqual({ id: 123 });
      });

      it('should return new INivel if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNivel = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNivel).toEqual(new Nivel());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Nivel })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNivel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNivel).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
