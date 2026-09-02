import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/Login.css";

function Login() {
    const navigate = useNavigate();

    const [employeeId, setEmployeeId] = useState("");
    const [password, setPassword] = useState("");

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    const handleSubmit = async (event) => {
        event.preventDefault();

        const loginData = {
            employeeId,
            password
        };

        try {
            const response = await fetch("/api/v1/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginData)
            });

            if (response.ok) {
                setToast({
                    message: "Login successful!",
                    type: "success"
                });

                setTimeout(() => {
                    navigate("/dashboard");
                }, 500);

            } else {
                const error = await response.text();

                setToast({
                    message: error || "Invalid employee ID or password.",
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

            <div className="login-container">

                <div className="login-card">

                    <h1>Employee Login</h1>

                    <form onSubmit={handleSubmit}>

                        <div className="form-group">

                            <label htmlFor="employeeId">
                                Employee ID
                            </label>

                            <input
                                type="text"
                                id="employeeId"
                                placeholder="Enter Employee ID"
                                value={employeeId}
                                onChange={(event) =>
                                    setEmployeeId(event.target.value)
                                }
                                required
                            />

                        </div>


                        <div className="form-group">

                            <label htmlFor="password">
                                Password
                            </label>

                            <input
                                type="password"
                                id="password"
                                placeholder="Enter Password"
                                value={password}
                                onChange={(event) =>
                                    setPassword(event.target.value)
                                }
                                required
                            />

                        </div>


                        <button
                            type="submit"
                            className="login-button"
                        >
                            Login
                        </button>


                        <button
                            type="button"
                            className="register-button"
                            onClick={() => navigate("/register")}
                        >
                            Create New Account
                        </button>

                    </form>

                </div>

            </div>
        </>
    );
}

export default Login;