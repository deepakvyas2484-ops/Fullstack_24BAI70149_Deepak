import React, { useState, useMemo, useCallback, memo } from "react";
import "./App.css";

const EventCard = memo(({ event, onDelete }) => {
  return (
    <div className="event-card">
      <div>
        <h3>{event.title}</h3>
        <p>{event.date}</p>
      </div>

      <button
        className="delete-btn"
        onClick={() => onDelete(event.id)}
      >
        🗑 Delete
      </button>
    </div>
  );
});

function App() {
  const [events, setEvents] = useState([
    {
      id: 1,
      title: "React Lab",
      date: "08 Aug 2026",
    },
    {
      id: 2,
      title: "DBMS Practical",
      date: "10 Aug 2026",
    },
    {
      id: 3,
      title: "Project Submission",
      date: "15 Aug 2026",
    },
  ]);

  const [title, setTitle] = useState("");
  const [date, setDate] = useState("");

  const totalEvents = useMemo(() => events.length, [events]);

  const addEvent = useCallback(() => {
    if (!title || !date) return;

    setEvents([
      ...events,
      {
        id: Date.now(),
        title,
        date,
      },
    ]);

    setTitle("");
    setDate("");
  }, [title, date, events]);

  const deleteEvent = useCallback((id) => {
    setEvents(events.filter((e) => e.id !== id));
  }, [events]);

  return (
    <div className="container">

      <h1>⚡ Performance Optimized Event Manager</h1>

      <div className="top">

        <div className="stats">

          <h2>{totalEvents}</h2>
          <p>Total Events</p>

        </div>

        <div className="form">

          <input
            type="text"
            placeholder="Event Name"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />

          <button onClick={addEvent}>
            ➕ Add Event
          </button>

        </div>

      </div>

      <div className="list">

        {events.map((event) => (
          <EventCard
            key={event.id}
            event={event}
            onDelete={deleteEvent}
          />
        ))}

      </div>

    </div>
  );
}

export default App;