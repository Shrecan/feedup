async function loadComments() {
    const list = document.getElementById("commentList");
    const response = await fetch("/comments/all");
    const comments = await response.json();

    if (!comments.length) {
        list.innerHTML = "<section class=\"panel\"><p class=\"muted\">No comments found.</p></section>";
        return;
    }

    list.innerHTML = comments.map(comment => `
        <a class="session-card" href="feedback.html?session=${comment.sessionLink}">
            <h3>${comment.name || "Name not provided"}</h3>
            <p><strong>Email:</strong> ${comment.email}</p>
            <p><strong>Session:</strong> ${comment.sessionName}</p>
            <p><strong>Timestamp:</strong> ${new Date(comment.timestamp).toLocaleString()}</p>
            <p><strong>Rating Summary:</strong> ${comment.ratingSummary}%</p>
            <p class="muted">${comment.comments || "No comment text provided."}</p>
        </a>
    `).join("");
}

loadComments();
