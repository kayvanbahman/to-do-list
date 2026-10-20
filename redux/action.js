import Axios, { errorMessage } from "../utils/request";
import { GET_LIST, SET_ERROR } from "./types";

const currentFilter = () =>
  typeof window !== "undefined" && new URLSearchParams(window.location.search).get("filter") === "active"
    ? "active"
    : "all";

const fail = (dispatch, error) => {
  dispatch({ type: SET_ERROR, payload: errorMessage(error) });
  throw error;
};

export const getList = (filter = currentFilter()) => async (dispatch) => {
  try {
    const response = await Axios.get("/todos", { params: { active: filter === "active" } });
    dispatch({ type: GET_LIST, payload: response.data.data });
    dispatch({ type: SET_ERROR, payload: null });
  } catch (error) {
    fail(dispatch, error);
  }
};

export const addItem = (text) => async (dispatch) => {
  try {
    await Axios.post("/todos", { text });
    await dispatch(getList());
  } catch (error) {
    fail(dispatch, error);
  }
};

export const editItem = ({ id, text }) => async (dispatch) => {
  try {
    await Axios.patch(`/todos/${encodeURIComponent(id)}`, { text });
    await dispatch(getList());
  } catch (error) {
    fail(dispatch, error);
  }
};

export const deleteItem = (id) => async (dispatch) => {
  try {
    await Axios.delete(`/todos/${encodeURIComponent(id)}`);
    await dispatch(getList());
  } catch (error) {
    fail(dispatch, error);
  }
};

export const doneItem = ({ id, done }) => async (dispatch) => {
  try {
    await Axios.patch(`/todos/${encodeURIComponent(id)}`, { done: !done });
    await dispatch(getList());
  } catch (error) {
    fail(dispatch, error);
  }
};

export const removeCompleted = (filter = currentFilter()) => async (dispatch) => {
  try {
    const response = await Axios.get("/todos");
    const completed = response.data.data.filter((item) => item.done);
    await Promise.all(completed.map((item) => Axios.delete(`/todos/${encodeURIComponent(item.id)}`)));
    await dispatch(getList(filter));
  } catch (error) {
    await dispatch(getList(filter)).catch(() => {});
    fail(dispatch, error);
  }
};
