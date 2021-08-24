import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AndarMapaDetailComponent } from './andar-mapa-detail.component';

describe('Component Tests', () => {
  describe('AndarMapa Management Detail Component', () => {
    let comp: AndarMapaDetailComponent;
    let fixture: ComponentFixture<AndarMapaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AndarMapaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ andarMapa: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AndarMapaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AndarMapaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load andarMapa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.andarMapa).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
