import { expect } from 'chai';

import intersperse from '../../src/js/helpers/intersperse.js';

describe('intersperse', () => {
  it('should return a new array of the correct length', () => {
    const myArr = ['a', 'b', 'c'];
    const interspersed = intersperse(myArr, ' ');
    expect(interspersed.length === 5).to.eql(true);
  });
  it('should have whitespace for every uneven element', () => {
    const myArr = ['a', 'b', 'c', 'a', 'b', 'c', 'a', 'b', 'c'];
    const interspersed = intersperse(myArr, ' ');
    const shouldPass = interspersed.reduce((prev, curr, i) => {
      if (i % 2 !== 0) {
        return curr === ' ';
      }
      return prev;
    }, false);
    expect(shouldPass).to.eql(true);
  });
  it('should have comma for every uneven element', () => {
    const myArr = ['a', 'b', 'c', 'a', 'b', 'c', 'a', 'b', 'c'];
    const interspersed = intersperse(myArr, ', ');
    const shouldPass = interspersed.reduce((prev, curr, i) => {
      if (i % 2 !== 0) {
        return curr === ', ';
      }
      return prev;
    }, false);
    expect(shouldPass).to.eql(true);
  });
});
