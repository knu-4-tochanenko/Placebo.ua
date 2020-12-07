import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DruginfoComponent } from './druginfo.component';

describe('DruginfoComponent', () => {
  let component: DruginfoComponent;
  let fixture: ComponentFixture<DruginfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DruginfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DruginfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
