function requireLogin() {
    if (sessionStorage.getItem("feedupLoggedIn") !== "true") {
        window.location.href = "login.html";
    }
}

function logout() {
    sessionStorage.removeItem("feedupLoggedIn");
    window.location.href = "login.html";
}
