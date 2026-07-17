# UniSchedule

A student timetable and reminder management system built with Spring Boot. Designed to help university students organize their class schedules and track assignment/task deadlines in one place.

## Features

- **Student Management** — register and manage student profiles
- **Timetable Management** — add class schedules with automatic overlap detection (prevents double-booking the same time slot)
- **Reminders** — track pending tasks and deadlines, mark them complete when done

## Tech Stack

- **Backend:** Java 25, Spring Boot 4.1.0
- **Database:** H2 (embedded, file-based — no separate database installation required)
- **Templating:** Thymeleaf
- **Build Tool:** Maven

## Getting Started

### Prerequisites

- Java 17 or higher (Java 25 recommended)
- No separate database installation needed — H2 runs embedded

### Running the app

1. Clone this repository