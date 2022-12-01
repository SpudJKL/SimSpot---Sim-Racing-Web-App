import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCrudListComponent } from './product-crud-list.component';

describe('ProductCrudListComponent', () => {
  let component: ProductCrudListComponent;
  let fixture: ComponentFixture<ProductCrudListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductCrudListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductCrudListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
