import React, { useState } from "react";
import classes from "../styles/todo.module.sass";
import { MdCheck, MdDeleteOutline, MdEdit } from "react-icons/md";
import { useDispatch } from "react-redux";
import { deleteItem, doneItem, editItem } from "../redux/action";

const ToDo = ({ data }) => {
  const [editMode, setEditMode] = useState(false);
  const [text, setText] = useState(data.text);
  const changeHandler = (e) => {
    setText(e.target.value);
  };
  const dispatch = useDispatch();

  const editHandler = async () => {
    if (!text.trim()) return;
    try {
      await dispatch(editItem({ id: data.id, text: text.trim() }));
      setEditMode(false);
    } catch {}
  };
  const deleteHandler = () => {
    dispatch(deleteItem(data.id)).catch(() => {});
  };
  const doneHandler = () => {
    dispatch(doneItem(data)).catch(() => {});
  };
  const handleEditMode = () => {
    if (!editMode) {
      setEditMode(true);
      setText(data.text);
    }
  };
  return (
    <div className={`${classes.todo} ${data.done ? classes.completed : ""}`}>
      {editMode ? (
        <form className={classes.editForm} onSubmit={(event) => { event.preventDefault(); editHandler(); }}>
          <input className={classes.input} aria-label="Edit task" autoFocus value={text}
            onChange={changeHandler} onKeyDown={(event) => {
              if (event.key === "Escape") { setEditMode(false); setText(data.text); }
            }} />
          <button type="submit" disabled={!text.trim()}>Save</button>
          <button type="button" onClick={() => { setEditMode(false); setText(data.text); }}>Cancel</button>
        </form>
      ) : (
        <>
          <button className={classes.status} type="button" onClick={doneHandler}
            aria-label={data.done ? `Mark ${data.text} as active` : `Complete ${data.text}`}
            aria-pressed={data.done}>
            {data.done && <MdCheck aria-hidden="true" />}
          </button>
          <span className={classes.text} onDoubleClick={handleEditMode}>{data.text}</span>
          <button className={classes.action} type="button" onClick={handleEditMode} aria-label={`Edit ${data.text}`}><MdEdit aria-hidden="true" /></button>
          <button className={`${classes.action} ${classes.delete}`} type="button" onClick={deleteHandler} aria-label={`Delete ${data.text}`}><MdDeleteOutline aria-hidden="true" /></button>
        </>
      )}
    </div>
  );
};

export default React.memo(ToDo);
