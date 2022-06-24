import React, { useState } from "react";
import classes from "../styles/todo.module.sass";
import { MdClose, MdOutlineCheck } from "react-icons/md";
import { useDispatch } from "react-redux";
import { addItem, deleteItem, doneItem, editItem } from "../redux/action";

const ToDo = ({ data }) => {
  const [editMode, setEditMode] = useState(false);
  const [text, setText] = useState(data.text);
  const changeHandler = (e) => {
    setText(e.target.value);
  };
  const dispatch = useDispatch();

  const editHandler = async () => {
    dispatch(
      editItem({
        id: data.id,
        text: text,
      })
    );
    setText("");
    setEditMode(false);
  };
  const deleteHandler = () => {
    dispatch(deleteItem(data.id));
  };
  const doneHandler = () => {
    dispatch(doneItem(data));
  };
  const handleEditMode = () => {
    if (!editMode) {
      setEditMode(true);
      setText(data.text);
    }
  };
  return (
    <div className={classes.todo} onDoubleClick={() => handleEditMode()}>
      {editMode ? (
        <input
          className={classes.input}
          value={text}
          onChange={changeHandler}
          onBlur={() => editHandler()}
          onKeyDown={(event) => {
            if (event.key === "Enter") editHandler();
          }}
        />
      ) : (
        <>
          <span className={classes.status} onClick={doneHandler}>
            {data.done && <MdOutlineCheck />}
          </span>
          <span>{data.text}</span>
          <MdClose onClick={deleteHandler} />
        </>
      )}
    </div>
  );
};

export default React.memo(ToDo);
