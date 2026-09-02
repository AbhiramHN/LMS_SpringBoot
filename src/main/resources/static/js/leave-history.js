async function loadLeaveHistory() {

    try {

        const response = await fetch("/leave/history");

        if (response.status === 401) {

            window.location.href = "login.html";
            return;
        }

        if (!response.ok) {

            const error = await response.text();

            showToast(
                error || "Unable to load leave history.",
                "error"
            );

            return;
        }

        const leaves = await response.json();

        const tableBody =
            document.getElementById("leaveHistoryBody");

        tableBody.innerHTML = "";

        if (leaves.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="6">
                        No leave history found.
                    </td>
                </tr>
            `;

            return;
        }

        leaves.forEach(function (leave) {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${leave.leaveType}</td>
                <td>${leave.fromDate}</td>
                <td>${leave.toDate}</td>
                <td>${leave.numberOfDays}</td>
                <td>${leave.reason}</td>
                <td>
                    <span class="status status-${leave.status}">
                        ${leave.status}
                    </span>
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


loadLeaveHistory();