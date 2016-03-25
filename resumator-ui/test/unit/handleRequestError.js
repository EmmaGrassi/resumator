import { expect } from 'chai';

import handleRequestError from '../../src/js/helpers/handleRequestError.js';

describe('handleRequestError', () => {
  it('should not return undefined', () => {
    const error = { status: 403 };
    const action = handleRequestError(error);
    expect(action).to.not.equal(undefined);
  });

  it('should return a function', () => {
    const error = { status: 403 };
    const action = handleRequestError(error);
    expect(action).to.be.a('function');
  });

  it('should return clearCookie function', () => {
    const error = { status: 403 };
    const action = handleRequestError(error);
    expect(action.name).to.be.a('string');
    expect(action.name).to.equal('clear');
  });
});
