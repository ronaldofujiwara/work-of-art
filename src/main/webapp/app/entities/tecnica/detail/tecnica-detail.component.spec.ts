import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TecnicaDetailComponent } from './tecnica-detail.component';

describe('Component Tests', () => {
  describe('Tecnica Management Detail Component', () => {
    let comp: TecnicaDetailComponent;
    let fixture: ComponentFixture<TecnicaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TecnicaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ tecnica: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TecnicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TecnicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tecnica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tecnica).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
