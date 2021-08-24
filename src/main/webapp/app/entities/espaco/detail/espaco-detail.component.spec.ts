import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EspacoDetailComponent } from './espaco-detail.component';

describe('Component Tests', () => {
  describe('Espaco Management Detail Component', () => {
    let comp: EspacoDetailComponent;
    let fixture: ComponentFixture<EspacoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EspacoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ espaco: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EspacoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EspacoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load espaco on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.espaco).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
