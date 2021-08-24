import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FuncaoArtistaDetailComponent } from './funcao-artista-detail.component';

describe('Component Tests', () => {
  describe('FuncaoArtista Management Detail Component', () => {
    let comp: FuncaoArtistaDetailComponent;
    let fixture: ComponentFixture<FuncaoArtistaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FuncaoArtistaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ funcaoArtista: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FuncaoArtistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuncaoArtistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load funcaoArtista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.funcaoArtista).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
