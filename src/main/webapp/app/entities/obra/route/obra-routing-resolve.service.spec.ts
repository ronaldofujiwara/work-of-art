jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IObra, Obra } from '../obra.model';
import { ObraService } from '../service/obra.service';

import { ObraRoutingResolveService } from './obra-routing-resolve.service';

describe('Service Tests', () => {
  describe('Obra routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ObraRoutingResolveService;
    let service: ObraService;
    let resultObra: IObra | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ObraRoutingResolveService);
      service = TestBed.inject(ObraService);
      resultObra = undefined;
    });

    describe('resolve', () => {
      it('should return IObra returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultObra = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultObra).toEqual({ id: 123 });
      });

      it('should return new IObra if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultObra = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultObra).toEqual(new Obra());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Obra })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultObra = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultObra).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
