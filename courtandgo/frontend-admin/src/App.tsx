import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import ScheduleEditor from "./pages/ScheduleEditor";
import Register from "./pages/Register";
import Login from "./pages/Login";
import CreateClub from "./pages/CreateClub";
import ClubsList from "./pages/ClubsList";
import UpdateClubPage from "./pages/UpdateClubPage";
import UpdateCourtsPage from "./pages/UpdateCourtsPage";
import CreateCourtsPage from "./pages/CreateCourtsPage";
import { RequireAuth } from "./pages/RequireAuth";  

function App() {
  return (
    <Router>
      <Routes>
        {/* Redirecionar "/" para "/register" */}
        <Route path="/" element={<Navigate to="/register" />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />

        <Route path="/dashboard" element={<RequireAuth><Dashboard /></RequireAuth>} />
        <Route path="/clubs/create" element={<RequireAuth><CreateClub /></RequireAuth>} />
        <Route path="/clubs" element={<RequireAuth><ClubsList /></RequireAuth>} />
        <Route path="/clubs/edit/:clubId" element={<RequireAuth><UpdateClubPage /></RequireAuth>} />
        <Route path="/clubs/:clubId/schedules" element={<RequireAuth><ScheduleEditor /></RequireAuth>} />
        <Route path="/clubs/:clubId/edit-courts" element={<RequireAuth><UpdateCourtsPage /></RequireAuth>} />
        <Route path="/clubs/:clubId/create-courts" element={<RequireAuth><CreateCourtsPage /></RequireAuth>} />

      </Routes>
    </Router>
  );
}

export default App;
