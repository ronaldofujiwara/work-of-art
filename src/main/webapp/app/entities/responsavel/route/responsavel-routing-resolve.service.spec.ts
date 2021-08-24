jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IResponsavel, Responsavel } from '../responsavel.model';
import { ResponsavelService } from '../service/responsavel.service';

import { ResponsavelRoutingResolveService } from './responsavel-routing-resolve.service';

describe('Service Tests', () => {
  describe('Responsavel routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ResponsavelRoutingResolveService;
    let service: ResponsavelService;
    let resultResponsavel: IResponsavel | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ResponsavelRoutingResolveService);
      service = TestBed.inject(ResponsavelService);
      resultResponsavel = undefined;
    });

    describe('resolve', () => {
      it('should return IResponsavel returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResponsavel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResponsavel).toEqual({ id: 123 });
      });

      it('should return new IResponsavel if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResponsavel = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultResponsavel).toEqual(new Responsavel());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Responsavel })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResponsavel = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResponsavel).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
