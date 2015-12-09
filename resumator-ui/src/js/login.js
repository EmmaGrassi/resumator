/**
 * Integration with Google OAuth2 login
 *
 * @author Carlo Sciolla
 */
function onSignIn(googleUser) {
    var idtoken = googleUser.getAuthResponse().id_token;
    document.cookie = "resumatorJWT=" + idtoken;

    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    navigationView.model.set("icon", profile.getImageUrl());
}