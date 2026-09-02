document.getElementById("loginForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const loginData = {
        employeeId: document.getElementById("employeeId").value,
        password: document.getElementById("password").value
    };

    try {

        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {

            showToast(
                "Login successful!",
                "success",
                "dashboard.html"
            );

        } else {

            const error = await response.text();

            showToast(error, "error");
        }

    } catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }

});