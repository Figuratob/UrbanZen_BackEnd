/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UrbanZenTestModule } from '../../../test.module';
import { StudioComponent } from 'app/entities/studio/studio.component';
import { StudioService } from 'app/entities/studio/studio.service';
import { Studio } from 'app/shared/model/studio.model';

describe('Component Tests', () => {
  describe('Studio Management Component', () => {
    let comp: StudioComponent;
    let fixture: ComponentFixture<StudioComponent>;
    let service: StudioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UrbanZenTestModule],
        declarations: [StudioComponent],
        providers: []
      })
        .overrideTemplate(StudioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Studio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.studios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
