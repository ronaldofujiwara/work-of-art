import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResponsavelDetailComponent } from './responsavel-detail.component';

describe('Component Tests', () => {
  describe('Responsavel Management Detail Component', () => {
    let comp: ResponsavelDetailComponent;
    let fixture: ComponentFixture<ResponsavelDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ResponsavelDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ responsavel: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ResponsavelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResponsavelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load responsavel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.responsavel).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
