import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NivelDetailComponent } from './nivel-detail.component';

describe('Component Tests', () => {
  describe('Nivel Management Detail Component', () => {
    let comp: NivelDetailComponent;
    let fixture: ComponentFixture<NivelDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NivelDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nivel: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NivelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nivel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nivel).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
