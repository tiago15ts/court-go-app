import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import ClubEditor from "./pages/ClubEditor";
import ScheduleEditor from "./pages/ScheduleEditor";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/clubs" element={<ClubEditor />} />
        <Route path="/schedules" element={<ScheduleEditor />} />
      </Routes>
    </Router>
  );
}

export default App;