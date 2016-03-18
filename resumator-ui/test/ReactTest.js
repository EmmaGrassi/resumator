const React = require('react');
const chai = require('chai');
const chaiEnzyme = require('chai-enzyme');
const enzyme = require('enzyme');
const mount = enzyme.mount;
const shallow = enzyme.shallow;

const Home = require('../src/js/components/public/home');
const expect = chai.expect;

describe('<Home />', () => {
  it('calls componentDidMount', () => {
    const wrapper = mount(<Home />);
    expect(Home.prototype.componentDidMount.calledOnce).to.equal(false);
  });
});
