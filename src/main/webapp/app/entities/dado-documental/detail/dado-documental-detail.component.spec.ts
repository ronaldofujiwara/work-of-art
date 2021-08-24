import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DadoDocumentalDetailComponent } from './dado-documental-detail.component';

describe('Component Tests', () => {
  describe('DadoDocumental Management Detail Component', () => {
    let comp: DadoDocumentalDetailComponent;
    let fixture: ComponentFixture<DadoDocumentalDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DadoDocumentalDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dadoDocumental: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DadoDocumentalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DadoDocumentalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dadoDocumental on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dadoDocumental).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
