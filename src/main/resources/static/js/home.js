async function loadSessions() {
    const list = document.getElementById("sessionList");
    const response = await fetch("/api/session/all");
    const sessions = await response.json();

    if (!sessions.length) {
        list.innerHTML = "<p class=\"muted\">No feedback sessions found.</p>";
        return;
    }

    list.innerHTML = sessions.map(session => `
        <section class="session-card">
            <span class="badge ${session.active ? "green" : "red"}">${session.active ? "Active" : "Expired"}</span>
            <h3>${session.sessionName}</h3>
            <p class="muted">Responses: ${session.feedbackCount}</p>
            <p class="muted">Created: ${new Date(session.createdAt).toLocaleString()}</p>
            <p class="muted">Expires: ${new Date(session.expiresAt).toLocaleString()}</p>
            <a class="btn secondary" href="feedback.html?session=${session.sessionLink}">Open Details</a>
            <button class="secondary" data-expire="${session.id}">Expire Now</button>
            <button data-delete="${session.id}">Delete</button>
        </section>
    `).join("");

    document.querySelectorAll("[data-delete]").forEach(button => {
        button.addEventListener("click", () => deleteSession(button.dataset.delete));
    });

    document.querySelectorAll("[data-expire]").forEach(button => {
        button.addEventListener("click", () => expireSession(button.dataset.expire));
    });
}

async function deleteSession(id) {
    const password = prompt("Enter admin password to delete this session:");
    if (password === null) {
        return;
    }

    const response = await fetch(`/session/${id}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ password })
    });

    if (!response.ok) {
        alert(await response.text());
        return;
    }

    loadSessions();
}

async function expireSession(id) {
    const response = await fetch(`/session/expire/${id}`, { method: "POST" });
    if (!response.ok) {
        alert(await response.text());
        return;
    }

    loadSessions();
}

loadSessions();
