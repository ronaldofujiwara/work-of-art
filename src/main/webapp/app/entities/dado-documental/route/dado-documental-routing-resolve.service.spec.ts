jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDadoDocumental, DadoDocumental } from '../dado-documental.model';
import { DadoDocumentalService } from '../service/dado-documental.service';

import { DadoDocumentalRoutingResolveService } from './dado-documental-routing-resolve.service';

describe('Service Tests', () => {
  describe('DadoDocumental routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DadoDocumentalRoutingResolveService;
    let service: DadoDocumentalService;
    let resultDadoDocumental: IDadoDocumental | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DadoDocumentalRoutingResolveService);
      service = TestBed.inject(DadoDocumentalService);
      resultDadoDocumental = undefined;
    });

    describe('resolve', () => {
      it('should return IDadoDocumental returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadoDocumental = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDadoDocumental).toEqual({ id: 123 });
      });

      it('should return new IDadoDocumental if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadoDocumental = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDadoDocumental).toEqual(new DadoDocumental());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DadoDocumental })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDadoDocumental = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDadoDocumental).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
