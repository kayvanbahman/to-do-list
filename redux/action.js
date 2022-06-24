import { request } from "http";
import Axios from "../utils/request";
import { GET_LIST } from "./types";
export const addItem = (payload) => async (dispatch) => {
  const res = await Axios.post("/ToDos", {
    text: payload,
    done: false,
  });
  dispatch(getList());
};
export const getList = (filter) => async (dispatch) => {
  try {
    console.log(filter, 123);
    const res = await Axios.get(
      "/ToDos" + (filter === "active" ? "/?done=false" : "")
    );
    dispatch({
      type: GET_LIST,
      payload: res.data,
    });
  } catch (e) {
    alert("something went wrong !!!");
  }
};
export const editItem = (payload) => async (dispatch) => {
  try {
    const res = await Axios.patch("/ToDos/" + payload.id, {
      text: payload.text,
    });
    dispatch(getList());
  } catch (e) {
    alert("something went wrong !!!");
  }
};
export const deleteItem = (payload) => async (dispatch) => {
  try {
    const res = await Axios.delete("/ToDos/" + payload);
    dispatch(getList());
  } catch (e) {
    alert("something went wrong !!!");
  }
};
export const doneItem = (payload) => async (dispatch) => {
  try {
    const res = await Axios.patch("/ToDos/" + payload.id, {
      done: !payload.done,
    });
    dispatch(getList());
  } catch (e) {
    alert("something went wrong !!!");
  }
};

export const removeCompleted = (payload) => async (dispatch, getStore) => {
  try {
    const { toDos } = getStore();
    const completeds = toDos.filter((it) => it.done);
    console.log(completeds, toDos);
    const reqs = completeds?.map((it) => Axios.delete("/ToDos/" + it.id));
    Promise.all(reqs).then(function (values) {
      dispatch(getList());
    });
  } catch (e) {
    alert("something went wrong !!!");
  }
};
