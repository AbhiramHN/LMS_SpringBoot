import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/RevokeLeave.css";

function RevokeLeave() {
    const navigate = useNavigate();

    const [leaves, setLeaves] = useState([]);

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    useEffect(() => {
        loadRevocableLeaves();
    }, []);

    const loadRevocableLeaves = async () => {
        try {
            const response = await fetch("/api/v1/leave/revocable");

            if (response.status === 401) {
                navigate("/");
                return;
            }

            if (response.status === 403) {
                setToast({
                    message: "Only Leads and Managers can revoke leaves.",
                    type: "error"
                });

                setTimeout(() => {
                    navigate("/dashboard");
                }, 500);

                return;
            }

            if (!response.ok) {
                const error = await response.text();

                setToast({
                    message: error || "Unable to load revocable leaves.",
                    type: "error"
                });

                return;
            }

            const leaveData = await response.json();
            setLeaves(leaveData);

        } catch (error) {
            console.error(error);

            setToast({
                message: "Unable to connect to the server.",
                type: "error"
            });
        }
    };

    const revokeLeave = async (leaveId) => {
        try {
            const response = await fetch(
                `/api/v1/leave/${leaveId}/revoke`,
                {
                    method: "PUT"
                }
            );

            if (response.status === 401) {
                navigate("/");
                return;
            }

            if (response.ok) {
                setToast({
                    message: "Leave revoked successfully.",
                    type: "success"
                });

                setTimeout(() => {
                    loadRevocableLeaves();
                }, 500);

            } else {
                const error = await response.text();

                setToast({
                    message: error || "Unable to revoke leave.",
                    type: "error"
                });
            }

        } catch (error) {
            console.error(error);

            setToast({
                message: "Unable to connect to the server.",
                type: "error"
            });
        }
    };

    return (
        <>
            <Toast
                message={toast.message}
                type={toast.type}
            />

            <div className="revoke-container">

                <div className="revoke-card">

                    <div className="page-header">

                        <h1>Revoke Leave</h1>

                        <button
                            type="button"
                            onClick={() => navigate("/dashboard")}
                        >
                            Back to Dashboard
                        </button>

                    </div>


                    <div className="table-container">

                        <table>

                            <thead>
                                <tr>
                                    <th>Employee ID</th>
                                    <th>Leave Type</th>
                                    <th>From Date</th>
                                    <th>To Date</th>
                                    <th>Days</th>
                                    <th>Reason</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>

                                {leaves.length === 0 ? (

                                    <tr>
                                        <td colSpan="7">
                                            No leaves available for revocation.
                                        </td>
                                    </tr>

                                ) : (

                                    leaves.map((leave) => (

                                        <tr key={leave.leaveId}>

                                            <td>
                                                {leave.employeeId}
                                            </td>

                                            <td>
                                                {leave.leaveType}
                                            </td>

                                            <td>
                                                {leave.fromDate}
                                            </td>

                                            <td>
                                                {leave.toDate}
                                            </td>

                                            <td>
                                                {leave.numberOfDays}
                                            </td>

                                            <td>
                                                {leave.reason}
                                            </td>

                                            <td>
                                                <button
                                                    className="revoke-button"
                                                    onClick={() =>
                                                        revokeLeave(
                                                            leave.leaveId
                                                        )
                                                    }
                                                >
                                                    Revoke
                                                </button>
                                            </td>

                                        </tr>

                                    ))

                                )}

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>
        </>
    );
}

export default RevokeLeave;