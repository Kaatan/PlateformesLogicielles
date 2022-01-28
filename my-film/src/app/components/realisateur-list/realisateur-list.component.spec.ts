import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealisateurListComponent } from './realisateur-list.component';

describe('RealisateurListComponent', () => {
  let component: RealisateurListComponent;
  let fixture: ComponentFixture<RealisateurListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealisateurListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RealisateurListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
