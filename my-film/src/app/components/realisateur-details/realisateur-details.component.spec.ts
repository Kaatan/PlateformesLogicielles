import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RealisateurDetailsComponent } from './realisateur-details.component';

describe('RealisateurDetailsComponent', () => {
  let component: RealisateurDetailsComponent;
  let fixture: ComponentFixture<RealisateurDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RealisateurDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RealisateurDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
