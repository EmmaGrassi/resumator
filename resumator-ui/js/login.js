/**
 * Integration with Google OAuth2 login
 *
 * @author Carlo Sciolla
 */
function setCookie(token){
    function inOneHour() {
        var now = new Date();
        var oneHour = now.getTime();
        oneHour += 3600 * 1000;
        now.setTime(oneHour);
        return now;
    }

    var expiry = inOneHour(token);
    document.cookie =
        'resumatorJWT=' + token +
        '; expires=' + expiry.toUTCString() +
        '; path=/';
}

function onSignIn(googleUser) {
    var idtoken = googleUser.getAuthResponse().id_token;
    setCookie(idtoken);

    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    navigationView.model.set('icon', profile.getImageUrl());
    navigationView.model.set('profile', profile);
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}