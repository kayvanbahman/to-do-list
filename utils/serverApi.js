import axios from "axios";

const backend = axios.create({
  baseURL: process.env.TODO_API_URL || "http://127.0.0.1:8086",
  timeout: 10000,
  validateStatus: () => true,
});

const cookieOptions = `Path=/; HttpOnly; SameSite=Lax${
  process.env.NODE_ENV === "production" ? "; Secure" : ""
}`;

const tokenCookies = (tokens) => [
  `todo_access=${encodeURIComponent(tokens.accessToken)}; ${cookieOptions}; Max-Age=${tokens.expiresInSeconds}`,
  `todo_refresh=${encodeURIComponent(tokens.refreshToken)}; ${cookieOptions}`,
];

const clearCookies = () => [
  `todo_access=; ${cookieOptions}; Max-Age=0`,
  `todo_refresh=; ${cookieOptions}; Max-Age=0`,
];

const sameOrigin = (req) => {
  if (!req.headers.origin) return true;
  try {
    return new URL(req.headers.origin).host === req.headers.host;
  } catch {
    return false;
  }
};

export async function authRequest(req, res) {
  if (!sameOrigin(req)) {
    return res.status(403).json({ success: false, error: { message: "Invalid origin" } });
  }
  const action = req.query.action;
  if (req.method !== "POST" || !["login", "register", "logout"].includes(action)) {
    return res.status(405).json({ success: false, error: { message: "Method not allowed" } });
  }

  try {
    if (action === "logout" && !req.cookies.todo_refresh) {
      res.setHeader("Set-Cookie", clearCookies());
      return res.status(200).json({ success: true, data: null, error: null });
    }
    const result = await backend.post(`/api/auth/${action}`,
      action === "logout" ? { refreshToken: req.cookies.todo_refresh } : req.body);
    if (action === "logout") {
      res.setHeader("Set-Cookie", clearCookies());
    } else if (result.status < 300 && result.data?.success) {
      res.setHeader("Set-Cookie", tokenCookies(result.data.data));
      return res.status(result.status).json({ success: true, data: null, error: null });
    }
    return res.status(result.status).json(result.data);
  } catch {
    if (action === "logout") res.setHeader("Set-Cookie", clearCookies());
    return res.status(502).json({ success: false, error: { message: "Backend unavailable" } });
  }
}

export async function todoRequest(req, res, path) {
  if (req.method !== "GET" && !sameOrigin(req)) {
    return res.status(403).json({ success: false, error: { message: "Invalid origin" } });
  }
  const send = (token) => backend.request({
    method: req.method,
    url: path,
    params: req.method === "GET" ? { active: req.query.active === "true" } : undefined,
    data: ["POST", "PATCH"].includes(req.method) ? req.body : undefined,
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  });

  try {
    let result = await send(req.cookies.todo_access);
    if ([401, 403].includes(result.status) && req.cookies.todo_refresh) {
      const refresh = await backend.post("/api/auth/refresh", {
        refreshToken: req.cookies.todo_refresh,
      });
      if (refresh.status < 300 && refresh.data?.success) {
        res.setHeader("Set-Cookie", tokenCookies(refresh.data.data));
        result = await send(refresh.data.data.accessToken);
      } else {
        res.setHeader("Set-Cookie", clearCookies());
      }
    }
    if ([401, 403].includes(result.status)) {
      return res.status(401).json({ success: false, error: { message: "Please sign in" } });
    }
    return res.status(result.status).json(result.data);
  } catch {
    return res.status(502).json({ success: false, error: { message: "Backend unavailable" } });
  }
}
