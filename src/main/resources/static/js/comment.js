async function loadComments() {
    const list = document.getElementById("commentList");
    const response = await fetch("/comments/all");
    const comments = await response.json();

    if (!comments.length) {
        list.innerHTML = "<section class=\"empty-comments\"><p>No comments found.</p></section>";
        return;
    }

    list.innerHTML = comments.map(comment => `
        <a class="comment-card" href="feedback.html?session=${comment.sessionLink}">
            <div class="comment-top">
                <div>
                    <h2>${comment.name || "Name not provided"}</h2>
                    <p class="comment-email">${comment.email}</p>
                </div>
            </div>
            <div class="comment-meta">
                <p><strong>Session:</strong> <span>${comment.sessionName}</span></p>
                <p><strong>Rating Summary:</strong> <span>${comment.ratingSummary}%</span></p>
                <p><strong>Submitted:</strong> <span>${new Date(comment.timestamp).toLocaleString()}</span></p>
            </div>
            <div class="comment-body">
                <p>${comment.comments || "No comment text provided."}</p>
            </div>
        </a>
    `).join("");
}

loadComments();
