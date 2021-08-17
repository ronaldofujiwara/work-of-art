import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AmbienteDetailComponent } from './ambiente-detail.component';

describe('Component Tests', () => {
  describe('Ambiente Management Detail Component', () => {
    let comp: AmbienteDetailComponent;
    let fixture: ComponentFixture<AmbienteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AmbienteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ambiente: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AmbienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AmbienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ambiente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ambiente).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
