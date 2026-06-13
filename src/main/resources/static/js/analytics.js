const analyticsParams = new URLSearchParams(window.location.search);
const analyticsSessionId = analyticsParams.get("session");
document.getElementById("backLink").href = `feedback.html?session=${analyticsSessionId}`;

async function loadAnalytics() {
    const response = await fetch(`/api/analytics/${analyticsSessionId}`);
    const analytics = await response.json();
    document.getElementById("analyticsMeta").textContent = `${analytics.sessionName} | Responses: ${analytics.feedbackCount}`;
    document.getElementById("analyticsContent").innerHTML = analytics.categories.map(category => `
        <section class="panel stack">
            <h2>${category.category}</h2>
            ${category.metrics.map(metric => `
                <div class="bar-line">
                    <strong>${metric.label}</strong>
                    <div class="bar-track"><div class="bar ${metric.color}" style="width:${metric.score}%"></div></div>
                    <span>${metric.score}%</span>
                </div>
            `).join("")}
        </section>
    `).join("");
}

loadAnalytics();
