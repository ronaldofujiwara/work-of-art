import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AcervoAtualDetailComponent } from './acervo-atual-detail.component';

describe('Component Tests', () => {
  describe('AcervoAtual Management Detail Component', () => {
    let comp: AcervoAtualDetailComponent;
    let fixture: ComponentFixture<AcervoAtualDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AcervoAtualDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ acervoAtual: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AcervoAtualDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AcervoAtualDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load acervoAtual on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.acervoAtual).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
