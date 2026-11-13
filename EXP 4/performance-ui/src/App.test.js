import { render, screen, fireEvent } from "@testing-library/react";
import App from "./App";

test("renders title", () => {
  render(<App />);
  expect(screen.getByText(/Performance Optimized Event Manager/i)).toBeInTheDocument();
});

test("adds event", () => {
  render(<App />);

  fireEvent.change(screen.getByPlaceholderText("Event Name"), {
    target: { value: "AI Workshop" },
  });

  fireEvent.change(screen.getByDisplayValue(""), {
    target: { value: "2026-08-20" },
  });

  fireEvent.click(screen.getByText(/Add Event/i));

  expect(screen.getByText("AI Workshop")).toBeInTheDocument();
});