import countries from '../data/countries';

export default function convertCountry(iso) {
  return countries[iso];
}
