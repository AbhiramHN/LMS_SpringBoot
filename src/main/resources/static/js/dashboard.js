async function loadDashboard() {

    try {

        const response = await fetch("/auth/me");

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (!response.ok) {

            showToast(
                "Unable to load user information.",
                "error"
            );

            return;
        }

        const employee = await response.json();

        document.getElementById("userName").textContent =
            employee.name;

        document.getElementById("employeeId").textContent =
            employee.employeeId;

        document.getElementById("designation").textContent =
            employee.designation;

        document.getElementById("employeeName").textContent =
            employee.name;


        const managerActions =
            document.getElementById("managerActions");

        if (employee.designation === "MANAGER")
        {
            managerActions.style.display = "contents";
        }
        else if (employee.designation === "LEAD")
        {
            managerActions.style.display = "contents";

            const generateReport =
                managerActions.querySelector(
                    'a[href="/generateReport"]'
                );

            if (generateReport)
            {
                generateReport.style.display = "none";
            }
        }
        else
        {
            managerActions.style.display = "none";
        }

    }
    catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }
}


document.getElementById("logoutButton")
    .addEventListener("click", async function(event) {

        event.preventDefault();

        const response = await fetch("/auth/logout", {
            method: "POST"
        });

        if (response.ok)
        {
            showToast(
                "Logout successful!",
                "success",
                "login.html"
            );
        }
    });


loadDashboard();