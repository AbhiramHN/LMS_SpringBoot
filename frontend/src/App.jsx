import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import RequestLeave from "./pages/RequestLeave";
import LeaveHistory from "./pages/LeaveHistory";
import ApproveLeave from "./pages/ApproveLeave";
import RevokeLeave from "./pages/RevokeLeave";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/request-leave" element={<RequestLeave />} />
                <Route path="/leave-history" element={<LeaveHistory />} />
                <Route path="/approve-leave" element={<ApproveLeave />} />
                <Route path="/revoke-leave" element={<RevokeLeave />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;