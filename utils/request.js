import axios from "axios";

const Axios = axios.create({
  baseURL: "/api",
  timeout: 10000,
});

Axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401 && typeof window !== "undefined") {
      window.dispatchEvent(new Event("todo:unauthorized"));
    }
    return Promise.reject(error);
  }
);

export const errorMessage = (error) =>
  error.response?.data?.error?.message || error.message || "Request failed";

export default Axios;
