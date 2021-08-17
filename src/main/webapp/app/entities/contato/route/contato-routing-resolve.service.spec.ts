jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IContato, Contato } from '../contato.model';
import { ContatoService } from '../service/contato.service';

import { ContatoRoutingResolveService } from './contato-routing-resolve.service';

describe('Service Tests', () => {
  describe('Contato routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ContatoRoutingResolveService;
    let service: ContatoService;
    let resultContato: IContato | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ContatoRoutingResolveService);
      service = TestBed.inject(ContatoService);
      resultContato = undefined;
    });

    describe('resolve', () => {
      it('should return IContato returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContato = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContato).toEqual({ id: 123 });
      });

      it('should return new IContato if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContato = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultContato).toEqual(new Contato());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Contato })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultContato = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultContato).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
