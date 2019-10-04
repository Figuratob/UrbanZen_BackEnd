/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UrbanZenTestModule } from '../../../test.module';
import { StudioDetailComponent } from 'app/entities/studio/studio-detail.component';
import { Studio } from 'app/shared/model/studio.model';

describe('Component Tests', () => {
  describe('Studio Management Detail Component', () => {
    let comp: StudioDetailComponent;
    let fixture: ComponentFixture<StudioDetailComponent>;
    const route = ({ data: of({ studio: new Studio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [StudioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StudioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
