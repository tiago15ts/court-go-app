import { Link } from "react-router-dom";

export default function Dashboard() {
  return (
    <div>
      <h1>Admin Dashboard</h1>
      <ul>
        <li><Link to="/clubs">Edit Clubs</Link></li>
        <li><Link to="/schedules">Edit Schedules</Link></li>
      </ul>
    </div>
  );
}