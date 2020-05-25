import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILessonTemplate } from 'app/shared/model/lesson-template.model';
import { LessonTemplateService } from './lesson-template.service';

@Component({
  selector: 'jhi-lesson-template-delete-dialog',
  templateUrl: './lesson-template-delete-dialog.component.html'
})
export class LessonTemplateDeleteDialogComponent {
  lessonTemplate: ILessonTemplate;

  constructor(
    protected lessonTemplateService: LessonTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lessonTemplateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lessonTemplateListModification',
        content: 'Deleted an lessonTemplate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lesson-template-delete-popup',
  template: ''
})
export class LessonTemplateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lessonTemplate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LessonTemplateDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.lessonTemplate = lessonTemplate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/lesson-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/lesson-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
