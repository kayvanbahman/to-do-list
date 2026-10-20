import { todoRequest } from "../../../utils/serverApi";

export default function handler(req, res) {
  if (!["PATCH", "DELETE"].includes(req.method)) {
    return res.status(405).json({ success: false, error: { message: "Method not allowed" } });
  }
  return todoRequest(req, res, `/api/todos/${encodeURIComponent(req.query.id)}`);
}
