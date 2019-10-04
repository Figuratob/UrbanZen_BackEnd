/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UrbanZenTestModule } from '../../../test.module';
import { LessonTemplateDeleteDialogComponent } from 'app/entities/lesson-template/lesson-template-delete-dialog.component';
import { LessonTemplateService } from 'app/entities/lesson-template/lesson-template.service';

describe('Component Tests', () => {
  describe('LessonTemplate Management Delete Component', () => {
    let comp: LessonTemplateDeleteDialogComponent;
    let fixture: ComponentFixture<LessonTemplateDeleteDialogComponent>;
    let service: LessonTemplateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [LessonTemplateDeleteDialogComponent]
      })
        .overrideTemplate(LessonTemplateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LessonTemplateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LessonTemplateService);
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
