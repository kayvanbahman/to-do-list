import React from "react";
import { useSelector } from "react-redux";
import ToDo from "./ToDo";
import classes from "../styles/todos.module.sass";
import Add from "./Add";
const ToDos = () => {
  const toDos = useSelector((store) => store.toDos);

  return (
    <div className={classes.todosBox}>
      <Add />
      {toDos.length ? (
        <ul className={classes.list}>
          {toDos.map((item) => <li key={item.id}><ToDo data={item} /></li>)}
        </ul>
      ) : (
        <div className={classes.empty}>
          <span className={classes.emptyIcon} aria-hidden="true">✓</span>
          <h3>All clear for now</h3>
          <p>Add a task above, or enjoy a little breathing room.</p>
        </div>
      )}
    </div>
  );
};

export default ToDos;
