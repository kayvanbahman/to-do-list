import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { addItem } from "../redux/action";
import classes from "../styles/add.module.sass";
const Add = () => {
  const dispatch = useDispatch();

  const [text, setText] = useState("");
  const addHandler = async () => {
    if (text.length) dispatch(addItem(text));
    setText("");
  };
  return (
    <div className={classes.addContainer}>
      <input
        placeholder="What needs to be done?"
        value={text}
        onChange={(e) => setText(e.target.value)}
        onBlur={() => addHandler()}
        onKeyDown={(event) => {
          if (event.key === "Enter") addHandler();
        }}
      />
    </div>
  );
};

export default Add;
