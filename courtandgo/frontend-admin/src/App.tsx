import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import ScheduleEditor from "./pages/ScheduleEditor";
import Register from "./pages/Register";
import Login from "./pages/Login";
import CreateClub from "./pages/CreateClub";
import ClubsList from "./pages/ClubsList";
import UpdateClubPage from "./pages/UpdateClubPage";
import UpdateCourtsPage from "./pages/UpdateCourtsPage";

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
        <Route path="/clubs" element={<ClubsList />} />
        <Route path="/clubs/edit/:clubId" element={<UpdateClubPage />} />
        <Route path="/clubs/:clubId/schedules" element={<ScheduleEditor />} />
        <Route path="/clubs/:clubId/edit-courts" element={<UpdateCourtsPage />} />

      </Routes>
    </Router>
  );
}

export default App;
