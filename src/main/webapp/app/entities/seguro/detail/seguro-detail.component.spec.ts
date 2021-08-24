import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeguroDetailComponent } from './seguro-detail.component';

describe('Component Tests', () => {
  describe('Seguro Management Detail Component', () => {
    let comp: SeguroDetailComponent;
    let fixture: ComponentFixture<SeguroDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SeguroDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ seguro: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SeguroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SeguroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load seguro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.seguro).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
