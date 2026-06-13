document.getElementById("loginBtn").addEventListener("click", async () => {
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("errorMessage");

    const response = await fetch("/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (!response.ok) {
        errorMessage.textContent = "Invalid username or password";
        return;
    }

    sessionStorage.setItem("feedupLoggedIn", "true");
    window.location.href = "home.html";
});

document.addEventListener("keydown", event => {
    if (event.key === "Enter") {
        document.getElementById("loginBtn").click();
    }
});
