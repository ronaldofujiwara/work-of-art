jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAreaDepto, AreaDepto } from '../area-depto.model';
import { AreaDeptoService } from '../service/area-depto.service';

import { AreaDeptoRoutingResolveService } from './area-depto-routing-resolve.service';

describe('Service Tests', () => {
  describe('AreaDepto routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AreaDeptoRoutingResolveService;
    let service: AreaDeptoService;
    let resultAreaDepto: IAreaDepto | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AreaDeptoRoutingResolveService);
      service = TestBed.inject(AreaDeptoService);
      resultAreaDepto = undefined;
    });

    describe('resolve', () => {
      it('should return IAreaDepto returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAreaDepto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAreaDepto).toEqual({ id: 123 });
      });

      it('should return new IAreaDepto if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAreaDepto = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAreaDepto).toEqual(new AreaDepto());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AreaDepto })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAreaDepto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAreaDepto).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
