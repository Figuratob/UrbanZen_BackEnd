/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LessonTemplateService } from 'app/entities/lesson-template/lesson-template.service';
import { ILessonTemplate, LessonTemplate, DayOfWeek } from 'app/shared/model/lesson-template.model';

describe('Service Tests', () => {
  describe('LessonTemplate Service', () => {
    let injector: TestBed;
    let service: LessonTemplateService;
    let httpMock: HttpTestingController;
    let elemDefault: ILessonTemplate;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(LessonTemplateService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LessonTemplate(
        0,
        DayOfWeek.MONDAY,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAA',
        0,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            repeatStartDate: currentDate.format(DATE_FORMAT),
            repeatUntilDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a LessonTemplate', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            repeatStartDate: currentDate.format(DATE_FORMAT),
            repeatUntilDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            repeatStartDate: currentDate,
            repeatUntilDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new LessonTemplate(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a LessonTemplate', async () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            startHour: 1,
            startMinute: 1,
            endHour: 1,
            endMinute: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            street: 'BBBBBB',
            city: 'BBBBBB',
            availableSpaces: 1,
            repeatStartDate: currentDate.format(DATE_FORMAT),
            repeatUntilDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            repeatStartDate: currentDate,
            repeatUntilDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of LessonTemplate', async () => {
        const returnedFromService = Object.assign(
          {
            dayOfWeek: 'BBBBBB',
            startHour: 1,
            startMinute: 1,
            endHour: 1,
            endMinute: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            street: 'BBBBBB',
            city: 'BBBBBB',
            availableSpaces: 1,
            repeatStartDate: currentDate.format(DATE_FORMAT),
            repeatUntilDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            repeatStartDate: currentDate,
            repeatUntilDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LessonTemplate', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
