import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/Dashboard.css";

function Dashboard() {
    const navigate = useNavigate();

    const [employee, setEmployee] = useState(null);

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            const response = await fetch("/api/v1/auth/me");

            if (response.status === 401) {
                navigate("/");
                return;
            }

            if (!response.ok) {
                setToast({
                    message: "Unable to load user information.",
                    type: "error"
                });
                return;
            }

            const employeeData = await response.json();
            setEmployee(employeeData);

        } catch (error) {
            console.error(error);

            setToast({
                message: "Unable to connect to the server.",
                type: "error"
            });
        }
    };

    const handleGenerateReport = async () => {
        try {
            const response = await fetch("/api/v1/generateReport");

            if (response.status === 401) {
                navigate("/");
                return;
            }

            if (response.status === 403) {
                setToast({
                    message: "Not Authorized.",
                    type: "error"
                });
                return;
            }

            if (response.ok) {
                setToast({
                    message: "Report generation started.",
                    type: "success"
                });
            } else {
                const error = await response.text();

                setToast({
                    message: error || "Unable to generate report.",
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

    const handleLogout = async () => {
        try {
            const response = await fetch("/api/v1/auth/logout", {
                method: "POST"
            });

            if (response.ok) {
                setToast({
                    message: "Logout successful!",
                    type: "success"
                });

                setTimeout(() => {
                    navigate("/");
                }, 500);

            } else {
                setToast({
                    message: "Unable to logout.",
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

    if (!employee) {
        return (
            <>
                <Toast
                    message={toast.message}
                    type={toast.type}
                />

                <div className="dashboard-loading">
                    Loading...
                </div>
            </>
        );
    }

    const isManager = employee.designation === "MANAGER";
    const isLead = employee.designation === "LEAD";

    return (
        <>
            <Toast
                message={toast.message}
                type={toast.type}
            />

            <div className="dashboard-container">

                <div className="dashboard-header">

                    <div>
                        <h1>Leave Management System</h1>
                    </div>

                    <div className="user-section">

                        <span className="user-name">
                            {employee.name}
                        </span>

                        <button
                            className="header-logout"
                            onClick={handleLogout}
                        >
                            Logout
                        </button>

                    </div>

                </div>


                <div className="dashboard-grid">

                    <div className="employee-card">

                        <h2>Profile</h2>

                        <div className="employee-details">

                            <div className="detail-item">
                                <span>Employee ID</span>
                                <strong>
                                    {employee.employeeId}
                                </strong>
                            </div>

                            <div className="detail-item">
                                <span>Designation</span>
                                <strong>
                                    {employee.designation}
                                </strong>
                            </div>

                            <div className="detail-item">
                                <span>Name</span>
                                <strong>
                                    {employee.name}
                                </strong>
                            </div>

                        </div>

                    </div>


                    <div className="actions-card">

                        <h2>Quick Actions</h2>

                        <div className="action-grid">

                            <button
                                onClick={() => navigate("/request-leave")}
                            >
                                Request Leave
                            </button>

                            <button
                                onClick={() => navigate("/leave-history")}
                            >
                                Leave History
                            </button>

                            {(isManager || isLead) && (
                                <>
                                    <button
                                        onClick={() => navigate("/approve-leave")}
                                    >
                                        Approve Leave
                                    </button>

                                    {isManager && (
                                        <button onClick={handleGenerateReport}>
                                            Generate Report
                                        </button>
                                    )}

                                    <button
                                        onClick={() =>
                                            navigate("/revoke-leave")
                                        }
                                    >
                                        Revoke Leave
                                    </button>
                                </>
                            )}

                        </div>

                    </div>

                </div>

            </div>
        </>
    );
}

export default Dashboard;