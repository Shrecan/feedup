const params = new URLSearchParams(window.location.search);
const sessionId = params.get("session");

const fields = [
    ["hygiene", "Cafeteria - Hygiene"],
    ["taste", "Cafeteria - Taste"],
    ["ingredients", "Cafeteria - Ingredients"],
    ["service", "Cafeteria - Service"],
    ["quantity", "Cafeteria - Quantity"],
    ["safety", "Transport - Safety"],
    ["availability", "Transport - Availability"],
    ["driver", "Transport - Driver"],
    ["cab", "Transport - Cab"],
    ["cleanliness", "Housekeeping - Cleanliness"],
    ["humidity", "Housekeeping - Humidity"],
    ["lighting", "Housekeeping - Lighting"],
    ["response", "Housekeeping - Response"]
];

const ratings = [
    ["Excellent", 8],
    ["Good", 6],
    ["Fair", 4],
    ["Average", 2],
    ["Poor", 1]
];

function renderQuestions() {
    const form = document.getElementById("feedbackForm");
    form.innerHTML = fields.map(([key, label]) => `
        <section class="question stack">
            <h3>${label}</h3>
            <div class="rating-row">
                ${ratings.map(([rating, value]) => `
                    <label><input type="radio" name="${key}" value="${value}">${rating}</label>
                `).join("")}
            </div>
        </section>
    `).join("");
}

function valueOf(name) {
    const selected = document.querySelector(`input[name="${name}"]:checked`);
    return selected ? Number(selected.value) : null;
}

async function loadSession() {
    if (!sessionId) {
        alert("Invalid session link.");
        window.location.href = "home.html";
        return;
    }

    const response = await fetch(`/api/session/${sessionId}`);
    if (!response.ok) {
        alert("Session not found or expired.");
        return;
    }

    const session = await response.json();
    document.getElementById("sessionName").textContent = session.sessionName;
    document.getElementById("createdDate").textContent = new Date(session.createdAt).toLocaleString();
    document.getElementById("expiryDate").textContent = new Date(session.expiresAt).toLocaleString();
}

document.getElementById("submitFeedbackBtn").addEventListener("click", async () => {
    const name = document.getElementById("nameInput").value.trim();
    if (!name) {
        alert("Please enter your name.");
        return;
    }

    const email = document.getElementById("emailInput").value.trim();
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        alert("Please enter a valid email.");
        return;
    }

    const payload = { sessionId, name, email, comments: document.getElementById("commentBox").value.trim() };
    for (const [key] of fields) {
        const value = valueOf(key);
        if (value === null) {
            alert("Please answer all questions.");
            return;
        }
        payload[key] = value;
    }

    const response = await fetch("/api/feedback/submit", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        alert(await response.text());
        return;
    }

    window.location.href = "response.html";
});

renderQuestions();
loadSession();
