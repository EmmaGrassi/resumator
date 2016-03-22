import { expect } from 'chai';

import convertCountry from '../../src/js/helpers/convertCountry.js';

describe('convertCountry', () => {
  it('should covert NL to Netherlands', () => {
    expect(convertCountry('NL')).to.eql('Netherlands');
  });
  it('should covert DE to Germany', () => {
    expect(convertCountry('DE')).to.eql('Germany');
  });
});
