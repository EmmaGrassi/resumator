// {
//     "firstName": "Ramon",
//     "formattedName": "Ramon Gebben",
//     "headline": "Front-end consultant at Sytac",
//     "industry": "Information Technology and Services",
//     "lastName": "Gebben",
//     "location": {
//         "country": {
//             "code": "nl"
//         },
//         "name": "Utrecht Area, Netherlands"
//     },
//     "pictureUrl": "https://media.licdn.com/mpr/mprx/0_fOJWg2YZ26Xq54dYL7Tb9UxZ2bOvLIU1uH3FZMtZ…O3dMxp4h_iNQ7cYLSpvv4jq5_izQ7x08SpEYJHJmC9MuMk_2pEXOS6HL9JLf7HadaZ5JFSgm6J",
//     "pictureUrls": {
//         "_total": 1,
//         "values": [
//             "https://media.licdn.com/mpr/mprx/0_1KOz6z8Vr82R_9YtNVilK7hMrhrg_4rliui--I8p…QTiPCghphaA4_N0NLni1NZTMPcyZTdPNNR7sAa3VXcy4TdClLR7ckzjslDDATY14Lxx9CpIEUG"
//         ]
//     },
//     "positions": {
//         "_total": 2,
//         "values": [
//             {
//                 "company": {
//                     "id": 626751,
//                     "industry": "Computer Software",
//                     "name": "Sytac IT Consulting",
//                     "size": "201-500 employees",
//                     "type": "Privately Held"
//                 },
//                 "id": 778515575,
//                 "isCurrent": true,
//                 "startDate": {
//                     "month": 3,
//                     "year": 2016
//                 },
//                 "title": "Front-end consultant"
//             },
//             {
//                 "company": {
//                     "id": 5258906,
//                     "industry": "Internet",
//                     "name": "Ra-Ge.NET",
//                     "size": "Myself Only",
//                     "type": "Public Company"
//                 },
//                 "id": 479039700,
//                 "isCurrent": true,
//                 "startDate": {
//                     "month": 10,
//                     "year": 2013
//                 },
//                 "summary": "Freelance web developer specialized in front end development and Ruby on Rails.",
//                 "title": "Entrepreneur"
//             }
//         ]
//     },
//     "publicProfileUrl": "https://www.linkedin.com/in/ramon-gebben-88039b85",
//     "summary": "I'm very passionate about everything within front-end development. I focus on developing sustainable and scaleable applications using Javascript.\n\nCurrently I work of a company called Touchtribe, which is a part of the Makerstreet network where we build web application and solutions for clients like Albumprinter, Rabobank and Zilveren Kruis.\n\nBesides my day job I also spend some time maintaining http://Daily-Javascript.com and organizing a weekly meetup http://www.meetup.com/Saturday-Morning-Coding-Utrecht/."
// }

export default function modelUserForImport(data) {
  console.log('---->>', data);
  return {
    name: data.firstName,
    surname: data.lastName,
    currentResidence: data.location.name,
    aboutMe: data.summary,
  };
}
