import { expect } from 'chai';

import isUpperCase from '../../src/js/helpers/isUpperCase.js';

describe('isUpperCase', () => {
  it('should return true for `SYTAC`', () => {
    expect(isUpperCase('SYTAC')).to.eql(true);
  });
  it('should return false for `sytac`', () => {
    expect(isUpperCase('sytac')).to.eql(false);
  });
});
