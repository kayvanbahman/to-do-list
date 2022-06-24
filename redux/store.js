import { createStore, applyMiddleware, Store } from "redux";
import { createWrapper } from "next-redux-wrapper";
import reducer from "./reducer";
import thunk from "redux-thunk";
const makeStore = () => createStore(reducer, applyMiddleware(thunk));

export const wrapper = createWrapper(makeStore, { debug: true });
