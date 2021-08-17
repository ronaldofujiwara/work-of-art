jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AreaDeptoService } from '../service/area-depto.service';

import { AreaDeptoDeleteDialogComponent } from './area-depto-delete-dialog.component';

describe('Component Tests', () => {
  describe('AreaDepto Management Delete Component', () => {
    let comp: AreaDeptoDeleteDialogComponent;
    let fixture: ComponentFixture<AreaDeptoDeleteDialogComponent>;
    let service: AreaDeptoService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AreaDeptoDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(AreaDeptoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AreaDeptoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AreaDeptoService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
