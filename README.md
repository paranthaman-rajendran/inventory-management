# Construction Inventory Management

A simple Spring Boot web application for managing vendors and construction inventory-related data. The project provides both server-rendered UI (Thymeleaf) and REST APIs for programmatic access.

## What this project does

- Manage vendor records (create, read, update, delete).
- Provide a Thymeleaf-based UI for vendor management (list, add, edit forms).
- Offer REST API endpoints for vendors and other resources (used for integrations).
- Includes basic unit/integration tests and H2 in-memory DB for test runs.

## Current backend status (as of Nov 2, 2025)

- Core backend implemented using Spring Boot with Spring Data JPA.
- `Vendor` entity, repository, service, and both REST and web controllers are present.
- Thymeleaf templates updated with Bootstrap 5 for a business-oriented construction theme (home page, vendor list, vendor form). Icons use Bootstrap Icons.
- Build: Gradle wrapper present (`gradlew.bat`). A local build verification step is included below.

Files of interest

- `src/main/java/com/yogi/inventorymanagement/` — application source (controllers, services, models).
- `src/main/resources/templates/` — Thymeleaf templates (home page, vendor pages).
- `src/main/resources/static/css/styles.css` — custom styles (if present).

## Tech stack

- Java 11+ (or compatible JDK used by the project)
- Spring Boot (Web, Data JPA, Thymeleaf)
- Gradle wrapper for build (`gradlew.bat` for Windows)
- H2 in-memory DB used in tests; MySQL or other DB for production configuration
- Thymeleaf templates, Bootstrap 5 (CDN), Bootstrap Icons (CDN)

## Environment & prerequisites

- Windows OS (PowerShell)
- JDK installed and `JAVA_HOME` configured
- Internet access (for Bootstrap and icon CDN), or else configure local assets

## Setup and run (Windows PowerShell)

1. Open PowerShell and change to the project folder:

```powershell
cd E:\developer\java\inventorymanagement
```

2. Build the project (skip tests for a quick compile):

```powershell
.\gradlew.bat build -x test
```

3. Run the application locally:

```powershell
# Run using the Gradle bootRun task
.\gradlew.bat bootRun

# Or run the produced jar (after successful build)
java -jar build\libs\inventorymanagement-*.jar
```

4. Open the web UI in a browser:

- Home page: http://localhost:8080/
- Vendor management: http://localhost:8080/vendors

## Running tests

To run unit and integration tests:

```powershell
.\gradlew.bat test
```

Test behaviour: the project is configured to use H2 in-memory database for tests; production DB settings (MySQL) may be present in `application.properties`.

## Common issues & troubleshooting

- Build fails due to JDK mismatch: ensure installed JDK version matches the project target (check `build.gradle` sourceCompatibility).
- Ports in use: default app port is 8080. Change `server.port` in `src/main/resources/application.properties` if needed.
- CDN resources not loading (Bootstrap/icons): ensure machine has internet access; otherwise, host assets locally and update templates to use local paths.
- Database config: if you want to use MySQL locally, update `application.properties` with JDBC URL, username, and password.

## Notes on UI

- The Thymeleaf templates have been updated to use Bootstrap 5 and Bootstrap Icons for a cleaner, construction-oriented look.
- Vendor form fields and descriptions are aligned for better usability.

## Next steps / suggestions

- Add server-side validation and show validation messages on the form.
- Add paging controls for vendor list with page numbers.
- Add vendor detail page and more inventory-related pages (product types, stock, projects).
- Optionally bundle Bootstrap locally to remove CDN dependency.

---

If you want, I can run a build now and report the output, or update the README with additional environment-specific notes (e.g., Dockerfile, MySQL example config).
