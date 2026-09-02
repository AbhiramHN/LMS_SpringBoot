import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/RequestLeave.css";

function RequestLeave() {
    const navigate = useNavigate();

    const [leaveType, setLeaveType] = useState("");
    const [fromDate, setFromDate] = useState("");
    const [toDate, setToDate] = useState("");
    const [reason, setReason] = useState("");

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    const handleSubmit = async (event) => {
        event.preventDefault();

        const leaveRequest = {
            leaveType,
            fromDate,
            toDate,
            reason
        };

        try {
            const response = await fetch("/api/v1/leave/apply", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(leaveRequest)
            });

            if (response.ok) {
                setToast({
                    message: "Leave request submitted successfully.",
                    type: "success"
                });

                setTimeout(() => {
                    navigate("/dashboard");
                }, 500);
            } else {
                const error = await response.text();

                setToast({
                    message: error || "Unable to submit leave request.",
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

            <div className="leave-container">

                <div className="leave-card">

                    <div className="page-header">

                        <h1>Request Leave</h1>

                        <button
                            type="button"
                            onClick={() => navigate("/dashboard")}
                        >
                            Back to Dashboard
                        </button>

                    </div>


                    <form
                        id="leaveForm"
                        onSubmit={handleSubmit}
                    >

                        <div className="form-group">

                            <label htmlFor="leaveType">
                                Leave Type
                            </label>

                            <select
                                id="leaveType"
                                value={leaveType}
                                onChange={(event) =>
                                    setLeaveType(event.target.value)
                                }
                                required
                            >
                                <option value="">
                                    Select Leave Type
                                </option>

                                <option value="CL">
                                    Casual Leave
                                </option>

                                <option value="EL">
                                    Earned Leave
                                </option>

                                <option value="SL">
                                    Sick Leave
                                </option>

                                <option value="ML">
                                    Maternity Leave
                                </option>

                                <option value="PL">
                                    Paternity Leave
                                </option>

                                <option value="LWP">
                                    Leave Without Pay
                                </option>
                            </select>

                        </div>


                        <div className="form-group">

                            <label htmlFor="fromDate">
                                From Date
                            </label>

                            <input
                                type="date"
                                id="fromDate"
                                value={fromDate}
                                onChange={(event) =>
                                    setFromDate(event.target.value)
                                }
                                required
                            />

                        </div>


                        <div className="form-group">

                            <label htmlFor="toDate">
                                To Date
                            </label>

                            <input
                                type="date"
                                id="toDate"
                                value={toDate}
                                onChange={(event) =>
                                    setToDate(event.target.value)
                                }
                                required
                            />

                        </div>


                        <div className="form-group">

                            <label htmlFor="reason">
                                Reason
                            </label>

                            <textarea
                                id="reason"
                                rows="5"
                                placeholder="Enter reason for leave"
                                value={reason}
                                onChange={(event) =>
                                    setReason(event.target.value)
                                }
                                required
                            />

                        </div>


                        <button type="submit">
                            Submit Leave Request
                        </button>

                    </form>

                </div>

            </div>
        </>
    );
}

export default RequestLeave;