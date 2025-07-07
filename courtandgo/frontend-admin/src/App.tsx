import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import ClubEditor from "./pages/ClubsEditor";
import ScheduleEditor from "./pages/ScheduleEditor";
import Register from "./pages/Register";
import Login from "./pages/Login";
import CreateClub from "./pages/CreateClub";

function App() {
  return (
    <Router>
      <Routes>
        {/* Redirecionar "/" para "/register" */}
        <Route path="/" element={<Navigate to="/register" />} />

        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/clubs/create" element={<CreateClub />} />
        <Route path="/clubs" element={<ClubEditor />} />
        <Route path="/schedules" element={<ScheduleEditor />} />
      </Routes>
    </Router>
  );
}

export default App;
