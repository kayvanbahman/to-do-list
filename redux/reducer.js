import { HYDRATE } from "next-redux-wrapper";
import { GET_LIST } from "./types";

const initialState = {
  toDos: [],
};
// create your reducer
const reducer = (state = initialState, action) => {
  switch (action.type) {
    case HYDRATE:
      return { ...state, ...action.payload };
    case GET_LIST:
      return { ...state, toDos: action.payload };

    default:
      return state;
  }
};
export default reducer;
