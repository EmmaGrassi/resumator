import { expect } from 'chai';

import getXSRFToken from '../../src/js/services/employee/helpers/getXSRFToken.js';
import create from '../../src/js/services/employee/create.js';

beforeEach(() => {
  const tokenTag = document.createElement('script');
  tokenTag.id = 'xsrf-token';
  tokenTag.innerHTML = 'thisisavalidtoken';
  document.body.appendChild(tokenTag);
});

describe('getXSRFToken', () => {
  it('should return the correct token', () => {
    const myToken = getXSRFToken();
    expect(myToken).to.equal('thisisavalidtoken');
  });
});
