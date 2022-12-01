import { TestBed } from '@angular/core/testing';

import { productCRUD } from './product-crud.service';

describe('ProductCRUD', () => {
  let service: productCRUD;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(productCRUD);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
