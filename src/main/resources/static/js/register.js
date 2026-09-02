document.getElementById("registerForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const employee = {
        name: document.getElementById("name").value,
        designation: document.getElementById("designation").value,
        age: parseInt(document.getElementById("age").value),
        gender: document.getElementById("gender").value,
        password: document.getElementById("password").value,
        joiningDate: new Date().toISOString().split("T")[0]
    };

    try {

        const response = await fetch("/employees/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(employee)
        });

        if (response.ok) {

            showToast(
                "Registration successful!",
                "success",
                "login.html"
            );

        } else {

            const error = await response.text();

            showToast(
                "Registration failed: " + error,
                "error"
            );
        }

    } catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }

});