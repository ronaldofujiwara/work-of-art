import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArtistaDetailComponent } from './artista-detail.component';

describe('Component Tests', () => {
  describe('Artista Management Detail Component', () => {
    let comp: ArtistaDetailComponent;
    let fixture: ComponentFixture<ArtistaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ArtistaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ artista: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ArtistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArtistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load artista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.artista).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
