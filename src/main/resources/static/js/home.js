async function loadSessions() {
    const list = document.getElementById("sessionList");
    const response = await fetch("/api/session/all");
    const sessions = await response.json();
    const intro = document.getElementById("homeIntro");

    if (!sessions.length) {
        intro.hidden = false;
        list.className = "sessions-empty";
        list.innerHTML = "<p>No feedback sessions found.</p>";
        return;
    }

    intro.hidden = true;
    list.className = "sessions-grid";
    list.innerHTML = sessions.map(session => `
        <section class="session-card">
            <div class="session-status-row">
                <span class="status-pill ${session.active ? "status-pill--active" : "status-pill--expired"}">${session.active ? "Active" : "Expired"}</span>
            </div>
            <div class="session-body">
                <h3>${session.sessionName}</h3>
                <dl class="session-meta">
                    <div>
                        <dt>Responses:</dt>
                        <dd>${session.feedbackCount}</dd>
                    </div>
                    <div>
                        <dt>Created:</dt>
                        <dd>${new Date(session.createdAt).toLocaleString()}</dd>
                    </div>
                    <div>
                        <dt>Expires:</dt>
                        <dd>${new Date(session.expiresAt).toLocaleString()}</dd>
                    </div>
                </dl>
            </div>
            <div class="session-actions">
                <a class="session-btn session-btn--light" href="feedback.html?session=${session.sessionLink}">Open Details</a>
                <button class="session-btn session-btn--light" type="button" data-expire="${session.id}">Expire Now</button>
                <button class="session-btn session-btn--danger" type="button" data-delete="${session.id}">Delete</button>
            </div>
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
