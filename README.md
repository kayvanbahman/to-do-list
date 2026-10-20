# Todo

Next.js frontend and Spring Boot API with JWT authentication. The frontend no longer uses `json-server` or `db.json` at runtime.

## Run locally

Start the backend and its dependencies as described in [backend/README.md](backend/README.md). The frontend's server-side API routes connect to `http://127.0.0.1:8086` (the MySQL profile) by default. To use the MongoDB profile on port 8087, set `TODO_API_URL=http://127.0.0.1:8087` when starting the frontend.

```bash
yarn install --frozen-lockfile
yarn dev
```

Open `http://localhost:3000`, register or sign in, and manage todos. The frontend uses same-origin API routes for auth and todo requests. Access and refresh tokens are kept in HttpOnly cookies; the server refreshes expired access tokens automatically.

This project currently uses Next.js 12. On this Mac, Node 24 runs it; the installed Node 26 fails during startup.
