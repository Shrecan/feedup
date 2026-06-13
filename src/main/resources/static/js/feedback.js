const feedbackParams = new URLSearchParams(window.location.search);
const feedbackSessionId = feedbackParams.get("session");

async function loadFeedbackSession() {
    const response = await fetch(`/api/session/${feedbackSessionId}`);
    const session = await response.json();
    document.getElementById("sessionTitle").textContent = session.sessionName;
    document.getElementById("sessionMeta").textContent = `Expires ${new Date(session.expiresAt).toLocaleString()}`;
    const status = document.getElementById("sessionStatus");
    status.textContent = session.active ? "Active" : "Expired";
    status.classList.toggle("status-pill--expired", !session.active);
    document.getElementById("reportLink").href = `report.html?session=${feedbackSessionId}`;
    document.getElementById("analyticsLink").href = `analytics.html?session=${feedbackSessionId}`;
}

document.getElementById("downloadBtn").addEventListener("click", () => {
    window.location.href = `/api/export/${feedbackSessionId}`;
});

loadFeedbackSession();
