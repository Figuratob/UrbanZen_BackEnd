/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UrbanZenTestModule } from '../../../test.module';
import { LessonTemplateUpdateComponent } from 'app/entities/lesson-template/lesson-template-update.component';
import { LessonTemplateService } from 'app/entities/lesson-template/lesson-template.service';
import { LessonTemplate } from 'app/shared/model/lesson-template.model';

describe('Component Tests', () => {
  describe('LessonTemplate Management Update Component', () => {
    let comp: LessonTemplateUpdateComponent;
    let fixture: ComponentFixture<LessonTemplateUpdateComponent>;
    let service: LessonTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [LessonTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LessonTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LessonTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LessonTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LessonTemplate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LessonTemplate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
