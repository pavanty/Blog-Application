import logo from './logo.svg';
import './App.css';
import Registration from './components/Registration';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'; // Import Navigate
import Login from './components/Login';
import BlogCategeories from './components/BlogCategeories';
import CreateCategory from './components/CreateCategory';
import AuthGuard from './components/AuthGuard';
import NavBar from './components/navigation/NavBar';

import { useEffect,useState } from 'react';
import EditBlogCategory from './components/EditBlogCategory';
import UserDashBoard from './components/UserDashBoard';
import CreateBlogs from './components/blogs/CreateBlogs';
import BlogView from './components/blogs/BlogView';
import BlogLists from './components/blogs/BlogLists';
import DeletedBlogs from './components/blogs/DeletedBlogs';
import EditBlogs from './components/blogs/EditBlogs';
import BlogCommentUpdate from './components/blogs/BlogCommentUpdate';


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(null);
  const [loading, setLoading] = useState(true); 
  
  useEffect(() => {
    const userData = JSON.parse(localStorage.getItem('userinformation'));

    if (userData) {
      setIsAuthenticated(true);
    } else {
      setIsAuthenticated(false);
    }

    // Once the check is complete, set loading to false
    setLoading(false);
  }, []);


  const handleLogout = () => {

    setIsAuthenticated(false);
  };
  if (loading) {
    // While loading, you can display a loading spinner or message
    return <div>Loading...</div>;
  }
  return (
    <Router>
      {isAuthenticated && <NavBar onLogout={handleLogout} />}
      <Routes>
        <Route path="/" element={<Registration />} />
        <Route path="/login" element={<Login onLogin={() => setIsAuthenticated(true)} />} />
    {/* iff i am using this except login and registration all the routes will be protected */}
        <Route
          path="/*"
          element={
            isAuthenticated ? (
              <AuthGuard isAuthenticated={isAuthenticated}>
                <Routes>
                  <Route path="userdashboard" element={<UserDashBoard />} />
                  <Route path="blogcategories" element={<BlogCategeories />} />
                  <Route path="createcategory" element={<CreateCategory />} />
                  <Route path="editblogcategory/:categoryid" element={<EditBlogCategory />} />
                  <Route path="createblogs" element={<CreateBlogs />} />
                  <Route path="blogview/:blogid" element={<BlogView />} />
                  <Route path="blogedit/:blogid" element={<EditBlogs />} />
                  <Route path="bloglists" element={<BlogLists />} />
                  <Route path="deletedblogs" element={<DeletedBlogs />} />
                  <Route path="commentupdate/:commentid" element={<BlogCommentUpdate />} />
                  {/* Add other protected routes here */}
                </Routes>
              </AuthGuard>
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
      </Routes>
    </Router>
  );
}

export default App;