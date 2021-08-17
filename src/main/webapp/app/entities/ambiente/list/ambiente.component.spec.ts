import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AmbienteService } from '../service/ambiente.service';

import { AmbienteComponent } from './ambiente.component';

describe('Component Tests', () => {
  describe('Ambiente Management Component', () => {
    let comp: AmbienteComponent;
    let fixture: ComponentFixture<AmbienteComponent>;
    let service: AmbienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AmbienteComponent],
      })
        .overrideTemplate(AmbienteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AmbienteComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AmbienteService);

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
      expect(comp.ambientes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
