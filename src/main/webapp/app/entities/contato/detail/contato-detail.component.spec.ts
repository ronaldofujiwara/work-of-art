import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContatoDetailComponent } from './contato-detail.component';

describe('Component Tests', () => {
  describe('Contato Management Detail Component', () => {
    let comp: ContatoDetailComponent;
    let fixture: ComponentFixture<ContatoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ContatoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ contato: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ContatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contato on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contato).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
