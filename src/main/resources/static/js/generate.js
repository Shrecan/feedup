const generatedLink = sessionStorage.getItem("generatedLink") || "";
const input = document.getElementById("generatedLink");
input.value = generatedLink;

document.getElementById("copyLinkBtn").addEventListener("click", async () => {
    await navigator.clipboard.writeText(input.value);
    document.getElementById("copyLinkBtn").textContent = "Copied";
});
