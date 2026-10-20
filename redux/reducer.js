import { HYDRATE } from "next-redux-wrapper";
import { GET_LIST, SET_ERROR } from "./types";

const initialState = {
  toDos: [],
  error: null,
};
// create your reducer
const reducer = (state = initialState, action) => {
  switch (action.type) {
    case HYDRATE:
      return { ...state, ...action.payload };
    case GET_LIST:
      return { ...state, toDos: action.payload };
    case SET_ERROR:
      return { ...state, error: action.payload };

    default:
      return state;
  }
};
export default reducer;
