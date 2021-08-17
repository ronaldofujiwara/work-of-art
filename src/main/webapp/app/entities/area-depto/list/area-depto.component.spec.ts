import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AreaDeptoService } from '../service/area-depto.service';

import { AreaDeptoComponent } from './area-depto.component';

describe('Component Tests', () => {
  describe('AreaDepto Management Component', () => {
    let comp: AreaDeptoComponent;
    let fixture: ComponentFixture<AreaDeptoComponent>;
    let service: AreaDeptoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AreaDeptoComponent],
      })
        .overrideTemplate(AreaDeptoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AreaDeptoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AreaDeptoService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.areaDeptos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
