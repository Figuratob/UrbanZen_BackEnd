/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UrbanZenTestModule } from '../../../test.module';
import { StudioDeleteDialogComponent } from 'app/entities/studio/studio-delete-dialog.component';
import { StudioService } from 'app/entities/studio/studio.service';

describe('Component Tests', () => {
  describe('Studio Management Delete Component', () => {
    let comp: StudioDeleteDialogComponent;
    let fixture: ComponentFixture<StudioDeleteDialogComponent>;
    let service: StudioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [StudioDeleteDialogComponent]
      })
        .overrideTemplate(StudioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudioService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
