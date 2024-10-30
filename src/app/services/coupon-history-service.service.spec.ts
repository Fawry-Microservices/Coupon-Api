import { TestBed } from '@angular/core/testing';

import { CouponsHistoryService } from './coupon-history-service.service';

describe('CouponHistoryServiceService', () => {
  let service: CouponsHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CouponsHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
