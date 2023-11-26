// import React from "react";
// import { render, fireEvent, screen } from "@testing-library/react";
// import Login from "../Login";

// describe("Login Component", () => {
//   it("renders the Login component", () => {
//     render(<Login />);
//     const loginText = screen.getByText("Log In");
//     expect(loginText).toBeInTheDocument();
//   });

//   it("handles form validation", () => {
//     render(<Login />);
//     const submitButton = screen.getByText("Submit");
//     fireEvent.click(submitButton);

//     expect(screen.getByText("Email is required")).toBeInTheDocument();
//     expect(screen.getByText("Password is required")).toBeInTheDocument();
//   });

//   it("handles successful login", () => {
//     const mockOnLogin = jest.fn();

//     render(<Login onLogin={mockOnLogin} />);
//     const submitButton = screen.getByText("Submit");

//     fireEvent.change(screen.getByPlaceholderText("Email Address"), {
//       target: { value: "test@example.com" },
//     });

//     fireEvent.change(screen.getByPlaceholderText("Password"), {
//       target: { value: "testpassword" },
//     });

//     fireEvent.click(submitButton);

//     expect(screen.getByText("Login Successfully")).toBeInTheDocument();
//     expect(mockOnLogin).toHaveBeenCalled();
//   });

//   it("handles login failure", () => {
//     render(<Login />);
//     const submitButton = screen.getByText("Submit");

//     fireEvent.change(screen.getByPlaceholderText("Email Address"), {
//       target: { value: "test@example.com" },
//     });

//     fireEvent.change(screen.getByPlaceholderText("Password"), {
//       target: { value: "testpassword" },
//     });

//     fireEvent.click(submitButton);

//     expect(screen.getByText("Login Failed")).toBeInTheDocument();
//   });
// });
