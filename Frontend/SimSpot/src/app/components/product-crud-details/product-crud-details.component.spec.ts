import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCrudDetailsComponent } from './product-crud-details.component';

describe('ProductCrudDetailsComponent', () => {
  let component: ProductCrudDetailsComponent;
  let fixture: ComponentFixture<ProductCrudDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductCrudDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductCrudDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
