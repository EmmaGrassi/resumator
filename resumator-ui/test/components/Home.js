import React from 'react';
import { mount, shallow } from 'enzyme';
import chai from 'chai';
import chaiEnzyme from 'chai-enzyme';

chai.use(chaiEnzyme());
const expect = chai.expect;

import Home from '../../src/js/components/public/Home';

describe('<Home />', () => {
  it('renders renders the Sytac logo', () => {
    const wrapper = shallow(<Home />);
    expect(
      wrapper.contains(<img src="http://sytac.io/images/sytac-hexagon-long.svg" />))
        .to.equal(true);
  });
});
