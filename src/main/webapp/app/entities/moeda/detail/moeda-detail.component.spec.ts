import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MoedaDetailComponent } from './moeda-detail.component';

describe('Component Tests', () => {
  describe('Moeda Management Detail Component', () => {
    let comp: MoedaDetailComponent;
    let fixture: ComponentFixture<MoedaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MoedaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ moeda: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MoedaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MoedaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load moeda on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.moeda).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
