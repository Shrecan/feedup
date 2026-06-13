const analyticsParams = new URLSearchParams(window.location.search);
const analyticsSessionId = analyticsParams.get("session");
document.getElementById("backLink").href = `feedback.html?session=${analyticsSessionId}`;

async function loadAnalytics() {
    const response = await fetch(`/api/analytics/${analyticsSessionId}`);
    const analytics = await response.json();
    document.getElementById("analyticsMeta").textContent = `${analytics.sessionName} | Responses: ${analytics.feedbackCount}`;
    document.getElementById("analyticsContent").innerHTML = analytics.categories.map(category => `
        <section class="analytics-card">
            <h2>${category.category}</h2>
            <div class="analytics-divider"></div>
            ${category.metrics.map(metric => `
                <div class="metric-row">
                    <strong>${metric.label}</strong>
                    <div class="metric-track">
                        <div class="metric-fill metric-fill--${metric.color}" style="width:${metric.score}%">
                            <span>${metric.score}%</span>
                        </div>
                    </div>
                </div>
            `).join("")}
        </section>
    `).join("");
}

loadAnalytics();
