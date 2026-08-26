async function loadRevocableLeaves() {

    try {

        const response = await fetch("/leave/revocable");

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (response.status === 403) {

            showToast(
                "Only Leads and Managers can revoke leaves.",
                "error",
                "dashboard.html"
            );

            return;
        }

        if (!response.ok) {

            const error = await response.text();

            showToast(
                error || "Unable to load revocable leaves.",
                "error"
            );

            return;
        }

        const leaves = await response.json();

        const tableBody =
            document.getElementById("revokeLeavesBody");

        tableBody.innerHTML = "";

        if (leaves.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        No leaves available for revocation.
                    </td>
                </tr>
            `;

            return;
        }

        leaves.forEach(function (leave) {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${leave.employeeId}</td>
                <td>${leave.leaveType}</td>
                <td>${leave.fromDate}</td>
                <td>${leave.toDate}</td>
                <td>${leave.numberOfDays}</td>
                <td>${leave.reason}</td>

                <td>
                    <button
                        class="revoke-button"
                        onclick="revokeLeave(${leave.leaveId})">
                        Revoke
                    </button>
                </td>
            `;

            tableBody.appendChild(row);
        });

    }
    catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }
}


async function revokeLeave(leaveId) {

    try {

        const response = await fetch(
            `/leave/${leaveId}/revoke`,
            {
                method: "PUT"
            }
        );

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (response.ok) {

            showToast(
                "Leave revoked successfully.",
                "success"
            );

            setTimeout(function () {
                loadRevocableLeaves();
            }, 3000);

        }
        else {

            const error = await response.text();

            showToast(
                error || "Unable to revoke leave.",
                "error"
            );
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


loadRevocableLeaves();