/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UrbanZenTestModule } from '../../../test.module';
import { LessonTemplateComponent } from 'app/entities/lesson-template/lesson-template.component';
import { LessonTemplateService } from 'app/entities/lesson-template/lesson-template.service';
import { LessonTemplate } from 'app/shared/model/lesson-template.model';

describe('Component Tests', () => {
  describe('LessonTemplate Management Component', () => {
    let comp: LessonTemplateComponent;
    let fixture: ComponentFixture<LessonTemplateComponent>;
    let service: LessonTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [LessonTemplateComponent],
        providers: []
      })
        .overrideTemplate(LessonTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LessonTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LessonTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LessonTemplate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lessonTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
