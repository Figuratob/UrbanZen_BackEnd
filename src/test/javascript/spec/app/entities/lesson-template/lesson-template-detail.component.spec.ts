/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UrbanZenTestModule } from '../../../test.module';
import { LessonTemplateDetailComponent } from 'app/entities/lesson-template/lesson-template-detail.component';
import { LessonTemplate } from 'app/shared/model/lesson-template.model';

describe('Component Tests', () => {
  describe('LessonTemplate Management Detail Component', () => {
    let comp: LessonTemplateDetailComponent;
    let fixture: ComponentFixture<LessonTemplateDetailComponent>;
    const route = ({ data: of({ lessonTemplate: new LessonTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [LessonTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LessonTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LessonTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lessonTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
