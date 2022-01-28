import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { Film } from '../models/film';

import { FilmService } from './film.service';

describe('FilmService', () => {
  let service: FilmService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilmService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

