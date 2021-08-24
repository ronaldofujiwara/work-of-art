jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAcervoAtual, AcervoAtual } from '../acervo-atual.model';
import { AcervoAtualService } from '../service/acervo-atual.service';

import { AcervoAtualRoutingResolveService } from './acervo-atual-routing-resolve.service';

describe('Service Tests', () => {
  describe('AcervoAtual routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AcervoAtualRoutingResolveService;
    let service: AcervoAtualService;
    let resultAcervoAtual: IAcervoAtual | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AcervoAtualRoutingResolveService);
      service = TestBed.inject(AcervoAtualService);
      resultAcervoAtual = undefined;
    });

    describe('resolve', () => {
      it('should return IAcervoAtual returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcervoAtual = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAcervoAtual).toEqual({ id: 123 });
      });

      it('should return new IAcervoAtual if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcervoAtual = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAcervoAtual).toEqual(new AcervoAtual());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AcervoAtual })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAcervoAtual = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAcervoAtual).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
