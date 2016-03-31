import { expect } from 'chai';

import stripStyles from '../../src/js/helpers/stripStyles.js';

const myValue = '<div><span style="color: rgb(255, 255, 255); font-family: Quattrocento Sans, Helvetica, Arial, sans-serif; font-size: 16px; background-color: rgb(16, 35, 114);">Old ladies die all the time. It s practically in the job description. Well, youre very similar heights. Maybe you should wear labels. Big scarf, bow tie, big embarrassing. No. That is not the question. That is not where we start. No sir. Thirteen! Come on, Team Not Dead. What do you think of the new look? I was hoping for minimalism, but I think I came up with magician. Mortuaries and larders. Easiest things to break out of. Shut up! Just shut up, shut up, shut up, shuttity up up up! I just have one question…&nbsp;do you know how to fly this thing?</span></div>';
const compareValue = '<div><span >Old ladies die all the time. It s practically in the job description. Well, youre very similar heights. Maybe you should wear labels. Big scarf, bow tie, big embarrassing. No. That is not the question. That is not where we start. No sir. Thirteen! Come on, Team Not Dead. What do you think of the new look? I was hoping for minimalism, but I think I came up with magician. Mortuaries and larders. Easiest things to break out of. Shut up! Just shut up, shut up, shut up, shuttity up up up! I just have one question…&nbsp;do you know how to fly this thing?</span></div>';
describe('stripStyles', () => {
  it('should remove all style attrs from text', () => {
    const strippedValue = stripStyles(myValue);
    expect(strippedValue).to.equal(compareValue);
  });
});
