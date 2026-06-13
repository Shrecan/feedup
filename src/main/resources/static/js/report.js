const reportParams = new URLSearchParams(window.location.search);
const pathParts = window.location.pathname.split("/").filter(Boolean);
const pathSessionId = pathParts[0] === "report" ? pathParts[1] : null;
const reportSessionId = reportParams.get("session") || pathSessionId;

const backLink = document.getElementById("backLink");
const sessionName = document.getElementById("sessionName");
const tableBody = document.getElementById("reportTableBody");

backLink.href = reportSessionId ? `feedback.html?session=${reportSessionId}` : "home.html";

const departmentConfig = {
    Cafeteria: {
        scoreThresholds: { green: 80, yellow: 60 },
        ranges: ["<60", "60-80", ">80"],
        parameters: [
            ["department", "Cafeteria Facility"],
            ["hygiene", "Hygiene"],
            ["taste", "Food Taste & Quality"],
            ["ingredients", "Food Ingredients"],
            ["service", "Service Quality"],
            ["quantity", "Food Quantity"]
        ]
    },
    Transport: {
        scoreThresholds: { green: 90, yellow: 70 },
        ranges: ["<70", "70-90", ">90"],
        parameters: [
            ["department", "Transport"],
            ["safety", "Safety"],
            ["availability", "Timely Availability"],
            ["driver", "Driver Behavior"],
            ["cab", "Cab Condition & Upkeep"]
        ]
    },
    Housekeeping: {
        scoreThresholds: { green: 85, yellow: 65 },
        ranges: ["<65", "65-85", ">85"],
        parameters: [
            ["department", "Housekeeping"],
            ["cleanliness", "Cleanliness"],
            ["humidity", "Humidity & Temperature"],
            ["lighting", "Lighting"],
            ["response", "Responsiveness"]
        ]
    }
};

function scoreClass(score, config) {
    if (score >= config.scoreThresholds.green) {
        return "green";
    }
    if (score >= config.scoreThresholds.yellow) {
        return "yellow";
    }
    return "red";
}

function scoreLabel(score, config) {
    const color = scoreClass(score, config);
    if (color === "green") {
        return "Green";
    }
    if (color === "yellow") {
        return "Amber";
    }
    return "Red";
}

function metricMap(category) {
    return Object.fromEntries(category.metrics.map(metric => [metric.key, metric]));
}

function renderDepartment(category) {
    const config = departmentConfig[category.category];
    if (!config) {
        return "";
    }

    const metrics = metricMap(category);
    const status = scoreClass(category.score, config);
    const statusText = scoreLabel(category.score, config);
    const rows = [];
    const parameterRows = config.parameters.length;

    config.parameters.forEach(([key, label], index) => {
        const isDepartmentRow = key === "department";
        const metric = metrics[key];
        const parameterCell = `<td class="${isDepartmentRow ? "department-cell" : "parameter-cell"}">${isDepartmentRow ? label : `-${label}`}</td>`;

        if (index === 0) {
            rows.push(`
                <tr>
                    ${parameterCell}
                    <td class="source-cell" rowspan="${parameterRows}">Feedback<br>forms</td>
                    <td class="rating-cell traffic-red" rowspan="2">Red</td>
                    <td class="basis-cell" rowspan="2">Way below target set</td>
                    <td class="range-cell" rowspan="2">${config.ranges[0]}</td>
                    <td class="score-cell status-${status}" rowspan="${parameterRows}">${category.score}%</td>
                    <td class="status-cell status-${status}" rowspan="${parameterRows}">${statusText}</td>
                </tr>
            `);
            return;
        }

        if (index === 2) {
            rows.push(`
                <tr>
                    ${parameterCell}
                    <td class="rating-cell traffic-yellow" rowspan="2">Amber</td>
                    <td class="basis-cell" rowspan="2">Near and around Target</td>
                    <td class="range-cell" rowspan="2">${config.ranges[1]}</td>
                </tr>
            `);
            return;
        }

        if (index === 4) {
            rows.push(`
                <tr>
                    ${parameterCell}
                    <td class="rating-cell traffic-green" rowspan="${parameterRows - 4}">Green</td>
                    <td class="basis-cell" rowspan="${parameterRows - 4}">Target reached or crossed</td>
                    <td class="range-cell" rowspan="${parameterRows - 4}">${config.ranges[2]}</td>
                </tr>
            `);
            return;
        }

        rows.push(`<tr>${parameterCell}</tr>`);
    });

    return rows.join("");
}

async function loadReport() {
    if (!reportSessionId) {
        tableBody.innerHTML = `<tr><td colspan="7" class="error-cell">Session not found.</td></tr>`;
        sessionName.textContent = "Missing session";
        return;
    }

    try {
        const response = await fetch(`/api/report/${reportSessionId}`);
        if (!response.ok) {
            throw new Error("Unable to load report data.");
        }

        const report = await response.json();
        sessionName.textContent = `${report.sessionName} | Responses: ${report.feedbackCount}`;
        tableBody.innerHTML = report.categories.map(renderDepartment).join("");
    } catch (error) {
        tableBody.innerHTML = `<tr><td colspan="7" class="error-cell">${error.message}</td></tr>`;
        sessionName.textContent = "Report unavailable";
    }
}

loadReport();
