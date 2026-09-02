function showToast(message, type = "info", redirectUrl = null) {

    const toast = document.createElement("div");

    toast.className = "toast " + type;
    toast.textContent = message;

    document.body.appendChild(toast);

    setTimeout(function () {

        toast.remove();

        if (redirectUrl) {
            window.location.href = redirectUrl;
        }

    }, 2000);
}