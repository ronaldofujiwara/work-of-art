jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITecnica, Tecnica } from '../tecnica.model';
import { TecnicaService } from '../service/tecnica.service';

import { TecnicaRoutingResolveService } from './tecnica-routing-resolve.service';

describe('Service Tests', () => {
  describe('Tecnica routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TecnicaRoutingResolveService;
    let service: TecnicaService;
    let resultTecnica: ITecnica | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TecnicaRoutingResolveService);
      service = TestBed.inject(TecnicaService);
      resultTecnica = undefined;
    });

    describe('resolve', () => {
      it('should return ITecnica returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTecnica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTecnica).toEqual({ id: 123 });
      });

      it('should return new ITecnica if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTecnica = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTecnica).toEqual(new Tecnica());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Tecnica })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTecnica = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTecnica).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
