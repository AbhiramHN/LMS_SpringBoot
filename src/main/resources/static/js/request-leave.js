document.getElementById("leaveForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const leaveRequest = {
        leaveType: document.getElementById("leaveType").value,
        fromDate: document.getElementById("fromDate").value,
        toDate: document.getElementById("toDate").value,
        reason: document.getElementById("reason").value
    };

    try {

        const response = await fetch("/leave/apply", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(leaveRequest)
        });

        if (response.ok) {

            showToast(
                "Leave request submitted successfully.",
                "success",
                "dashboard.html"
            );

        } else {

            const error = await response.text();

            showToast(
                error || "Unable to submit leave request.",
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