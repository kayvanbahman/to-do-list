import React, { useState } from "react";
import { MdAdd } from "react-icons/md";
import { useDispatch } from "react-redux";
import { addItem } from "../redux/action";
import classes from "../styles/add.module.sass";
const Add = () => {
  const dispatch = useDispatch();

  const [text, setText] = useState("");
  const [pending, setPending] = useState(false);
  const addHandler = async (event) => {
    event.preventDefault();
    if (!text.trim() || pending) return;
    setPending(true);
    try {
      await dispatch(addItem(text.trim()));
      setText("");
    } catch {
    } finally {
      setPending(false);
    }
  };
  return (
    <form className={classes.addContainer} onSubmit={addHandler}>
      <span className={classes.addIcon} aria-hidden="true"><MdAdd /></span>
      <input
        aria-label="New task"
        placeholder="Add a new task..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <button type="submit" disabled={!text.trim() || pending}>{pending ? "Adding..." : "Add task"}</button>
    </form>
  );
};

export default Add;
