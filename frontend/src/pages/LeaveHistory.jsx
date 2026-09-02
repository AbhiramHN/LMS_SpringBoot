import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/LeaveHistory.css";

function LeaveHistory() {
    const navigate = useNavigate();

    const [leaves, setLeaves] = useState([]);

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    useEffect(() => {
        loadLeaveHistory();
    }, []);

    const loadLeaveHistory = async () => {
        try {
            const response = await fetch("/api/v1/leave/history");

            if (response.status === 401) {
                navigate("/");
                return;
            }

            if (!response.ok) {
                const error = await response.text();

                setToast({
                    message: error || "Unable to load leave history.",
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

    return (
        <>
            <Toast
                message={toast.message}
                type={toast.type}
            />

            <div className="history-container">

                <div className="history-card">

                    <div className="page-header">

                        <h1>Leave History</h1>

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
                                    <th>Leave Type</th>
                                    <th>From Date</th>
                                    <th>To Date</th>
                                    <th>Days</th>
                                    <th>Reason</th>
                                    <th>Status</th>
                                </tr>
                            </thead>

                            <tbody>

                                {leaves.length === 0 ? (

                                    <tr>
                                        <td colSpan="6">
                                            No leave history found.
                                        </td>
                                    </tr>

                                ) : (

                                    leaves.map((leave) => (

                                        <tr key={leave.leaveId}>

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
                                                <span
                                                    className={`status status-${leave.status}`}
                                                >
                                                    {leave.status}
                                                </span>
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

export default LeaveHistory;