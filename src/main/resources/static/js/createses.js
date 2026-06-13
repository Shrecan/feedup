document.getElementById("generateLinkBtn").addEventListener("click", async () => {
    const sessionName = document.getElementById("sessionName").value.trim();
    const message = document.getElementById("message");

    if (!sessionName) {
        message.textContent = "Session name is required.";
        return;
    }

    const response = await fetch("/api/session/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ sessionName })
    });

    if (!response.ok) {
        message.textContent = await response.text();
        return;
    }

    const session = await response.json();
    sessionStorage.setItem("generatedLink", session.clientUrl);
    window.location.href = "generate.html";
});
