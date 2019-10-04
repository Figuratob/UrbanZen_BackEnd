/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UrbanZenTestModule } from '../../../test.module';
import { LessonComponent } from 'app/entities/lesson/lesson.component';
import { LessonService } from 'app/entities/lesson/lesson.service';
import { Lesson } from 'app/shared/model/lesson.model';

describe('Component Tests', () => {
  describe('Lesson Management Component', () => {
    let comp: LessonComponent;
    let fixture: ComponentFixture<LessonComponent>;
    let service: LessonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [LessonComponent],
        providers: []
      })
        .overrideTemplate(LessonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LessonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LessonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Lesson(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lessons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
