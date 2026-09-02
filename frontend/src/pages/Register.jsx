import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Toast from "../components/Toast";
import "../css/Register.css";

function Register() {
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [designation, setDesignation] = useState("");
    const [age, setAge] = useState("");
    const [gender, setGender] = useState("");
    const [password, setPassword] = useState("");

    const [nameError, setNameError] = useState("");
    const [ageError, setAgeError] = useState("");
    const [designationError, setDesignationError] = useState("");
    const [genderError, setGenderError] = useState("");
    const [passwordError, setPasswordError] = useState("");

    const [toast, setToast] = useState({
        message: "",
        type: "info"
    });

    const validateName = (value) => {
        if (!value.trim()) {
            return "Name is required";
        }

        if (!/^[A-Za-z ]+$/.test(value)) {
            return "Name should contain only letters and spaces";
        }

        return "";
    };

    const validateAge = (value) => {
        if (!value) {
            return "Age is required";
        }

        if (!/^\d+$/.test(value)) {
            return "Age should contain only numbers";
        }

        const numericAge = Number(value);

        if (numericAge < 18 || numericAge > 60) {
            return "Age must be between 18 and 60";
        }

        return "";
    };

    const validateDesignation = (value) => {
        if (!value) {
            return "Please select a designation";
        }

        return "";
    };

    const validateGender = (value) => {
        if (!value) {
            return "Please select a gender";
        }

        return "";
    };

    const validatePassword = (value) => {
        if (!value) {
            return "Password is required";
        }

        return "";
    };

    const handleNameChange = (event) => {
        const value = event.target.value;

        setName(value);
        setNameError(validateName(value));
    };

    const handleAgeChange = (event) => {
        const value = event.target.value;

        if (!/^\d*$/.test(value)) {
            return;
        }

        setAge(value);
        setAgeError(validateAge(value));
    };

    const handleDesignationChange = (event) => {
        const value = event.target.value;

        setDesignation(value);
        setDesignationError(validateDesignation(value));
    };

    const handleGenderChange = (event) => {
        const value = event.target.value;

        setGender(value);
        setGenderError(validateGender(value));
    };

    const handlePasswordChange = (event) => {
        const value = event.target.value;

        setPassword(value);
        setPasswordError(validatePassword(value));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const nameValidation = validateName(name);
        const ageValidation = validateAge(age);
        const designationValidation = validateDesignation(designation);
        const genderValidation = validateGender(gender);
        const passwordValidation = validatePassword(password);

        setNameError(nameValidation);
        setAgeError(ageValidation);
        setDesignationError(designationValidation);
        setGenderError(genderValidation);
        setPasswordError(passwordValidation);

        if (
            nameValidation ||
            ageValidation ||
            designationValidation ||
            genderValidation ||
            passwordValidation
        ) {
            return;
        }

        const employee = {
            name: name.trim(),
            designation,
            age: parseInt(age, 10),
            gender,
            password,
            joiningDate: new Date().toISOString().split("T")[0]
        };

        try {
            const response = await fetch("/api/v1/employees/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(employee)
            });

            if (response.ok) {
                setToast({
                    message: "Registration successful!",
                    type: "success"
                });

                setTimeout(() => {
                    navigate("/");
                }, 500);

            } else {
                const error = await response.text();

                setToast({
                    message: error || "Registration failed",
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

            <div className="register-container">
                <div className="register-card">

                    <h1>Employee Registration</h1>

                    <form id="registerForm" onSubmit={handleSubmit}>

                        <label htmlFor="name">
                            Name
                        </label>

                        <input
                            type="text"
                            id="name"
                            placeholder="Enter your name"
                            value={name}
                            onChange={handleNameChange}
                        />

                        {nameError && (
                            <span className="error-message">
                                {nameError}
                            </span>
                        )}


                        <label htmlFor="designation">
                            Designation
                        </label>

                        <select
                            id="designation"
                            value={designation}
                            onChange={handleDesignationChange}
                        >
                            <option value="">
                                Select Designation
                            </option>

                            <option value="EXECUTIVE">
                                Executive
                            </option>

                            <option value="LEAD">
                                Lead
                            </option>

                            <option value="MANAGER">
                                Manager
                            </option>
                        </select>

                        {designationError && (
                            <span className="error-message">
                                {designationError}
                            </span>
                        )}


                        <label htmlFor="age">
                            Age
                        </label>

                        <input
                            type="number"
                            id="age"
                            placeholder="Enter your age"
                            min="18"
                            max="60"
                            value={age}
                            onChange={handleAgeChange}
                        />

                        {ageError && (
                            <span className="error-message">
                                {ageError}
                            </span>
                        )}


                        <label htmlFor="gender">
                            Gender
                        </label>

                        <select
                            id="gender"
                            value={gender}
                            onChange={handleGenderChange}
                        >
                            <option value="">
                                Select Gender
                            </option>

                            <option value="MALE">
                                Male
                            </option>

                            <option value="FEMALE">
                                Female
                            </option>
                        </select>

                        {genderError && (
                            <span className="error-message">
                                {genderError}
                            </span>
                        )}


                        <label htmlFor="password">
                            Password
                        </label>

                        <input
                            type="password"
                            id="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={handlePasswordChange}
                        />

                        {passwordError && (
                            <span className="error-message">
                                {passwordError}
                            </span>
                        )}


                        <button type="submit">
                            Register
                        </button>

                        <button
                            type="button"
                            onClick={() => navigate("/")}
                        >
                            Go to Login
                        </button>

                    </form>

                </div>
            </div>
        </>
    );
}

export default Register;