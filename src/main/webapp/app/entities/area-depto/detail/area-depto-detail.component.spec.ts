import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AreaDeptoDetailComponent } from './area-depto-detail.component';

describe('Component Tests', () => {
  describe('AreaDepto Management Detail Component', () => {
    let comp: AreaDeptoDetailComponent;
    let fixture: ComponentFixture<AreaDeptoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AreaDeptoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ areaDepto: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AreaDeptoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AreaDeptoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load areaDepto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.areaDepto).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
