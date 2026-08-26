async function loadPendingLeaves() {

    try {

        const response = await fetch("/leave/pending");

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (response.status === 403) {

            showToast(
                "Only Leads and Managers can view pending leaves.",
                "error",
                "dashboard.html"
            );

            return;
        }

        if (!response.ok) {

            const error = await response.text();

            showToast(
                error || "Unable to load pending leaves.",
                "error"
            );

            return;
        }

        const leaves = await response.json();

        const tableBody =
            document.getElementById("pendingLeavesBody");

        tableBody.innerHTML = "";

        if (leaves.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        No pending leave requests.
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
                    <div class="action-buttons">

                        <button
                            class="approve-button"
                            onclick="approveLeave(${leave.leaveId})">
                            Approve
                        </button>

                        <button
                            class="reject-button"
                            onclick="rejectLeave(${leave.leaveId})">
                            Reject
                        </button>

                    </div>
                </td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }
}


async function approveLeave(leaveId) {

    await processLeave(
        leaveId,
        "approve"
    );
}


async function rejectLeave(leaveId) {

    await processLeave(
        leaveId,
        "reject"
    );
}


async function processLeave(leaveId, action) {

    try {

        const response = await fetch(
            `/leave/${leaveId}/${action}`,
            {
                method: "PUT"
            }
        );

        if (response.ok) {

            showToast(
                action === "approve"
                    ? "Leave approved successfully."
                    : "Leave rejected successfully.",
                "success"
            );

            setTimeout(function () {
                loadPendingLeaves();
            }, 3000);

        } else if (response.status === 401) {

            window.location.href = "login.html";

        } else {

            const error = await response.text();

            showToast(
                error || "Unable to process leave request.",
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
}


loadPendingLeaves();async function loadPendingLeaves() {

    try {

        const response = await fetch("/leave/pending");

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (response.status === 403) {

            showToast(
                "Only Leads and Managers can view pending leaves.",
                "error",
                "dashboard.html"
            );

            return;
        }

        if (!response.ok) {

            const error = await response.text();

            showToast(
                error || "Unable to load pending leaves.",
                "error"
            );

            return;
        }

        const leaves = await response.json();

        const tableBody =
            document.getElementById("pendingLeavesBody");

        tableBody.innerHTML = "";

        if (leaves.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        No pending leave requests.
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
                    <div class="action-buttons">

                        <button
                            class="approve-button"
                            onclick="approveLeave(${leave.leaveId})">
                            Approve
                        </button>

                        <button
                            class="reject-button"
                            onclick="rejectLeave(${leave.leaveId})">
                            Reject
                        </button>

                    </div>
                </td>
            `;

            tableBody.appendChild(row);
        });

    } catch (error) {

        console.error(error);

        showToast(
            "Unable to connect to the server.",
            "error"
        );
    }
}


async function approveLeave(leaveId) {

    await processLeave(
        leaveId,
        "approve"
    );
}


async function rejectLeave(leaveId) {

    await processLeave(
        leaveId,
        "reject"
    );
}


async function processLeave(leaveId, action) {

    try {

        const response = await fetch(
            `/leave/${leaveId}/${action}`,
            {
                method: "PUT"
            }
        );

        if (response.ok) {

            showToast(
                action === "approve"
                    ? "Leave approved successfully."
                    : "Leave rejected successfully.",
                "success"
            );

            setTimeout(function () {
                loadPendingLeaves();
            }, 3000);

        } else if (response.status === 401) {

            window.location.href = "login.html";

        } else {

            const error = await response.text();

            showToast(
                error || "Unable to process leave request.",
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
}


loadPendingLeaves();