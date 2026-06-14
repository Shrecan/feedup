// const generatedLink = sessionStorage.getItem("generatedLink") || "";
// const input = document.getElementById("generatedLink");
// input.value = generatedLink;

// document.getElementById("copyLinkBtn").addEventListener("click", async () => {
//     await navigator.clipboard.writeText(input.value);
//     document.getElementById("copyLinkBtn").textContent = "Copied";
// });
document.getElementById("copyLinkBtn").addEventListener("click", () => {
    const text = document.getElementById("generatedLink").value;

    if (navigator.clipboard && window.isSecureContext) {
        navigator.clipboard.writeText(text).then(() => {
            document.getElementById("copyLinkBtn").textContent = "Copied";
        }).catch(fallbackCopy);
    } else {
        fallbackCopy(text);
    }
});

function fallbackCopy(text) {
    const tempInput = document.createElement("textarea");
    tempInput.value = text;
    document.body.appendChild(tempInput);
    tempInput.select();
    document.execCommand("copy");
    document.body.removeChild(tempInput);

    document.getElementById("copyLinkBtn").textContent = "Copied";
}
