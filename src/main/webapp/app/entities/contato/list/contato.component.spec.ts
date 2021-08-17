import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ContatoService } from '../service/contato.service';

import { ContatoComponent } from './contato.component';

describe('Component Tests', () => {
  describe('Contato Management Component', () => {
    let comp: ContatoComponent;
    let fixture: ComponentFixture<ContatoComponent>;
    let service: ContatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ContatoComponent],
      })
        .overrideTemplate(ContatoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContatoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ContatoService);

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
      expect(comp.contatoes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
