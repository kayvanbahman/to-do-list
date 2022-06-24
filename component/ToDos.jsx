import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import ToDo from "./ToDo";
import classes from "../styles/todos.module.sass";
import Add from "./Add";
const ToDos = (props) => {
  const dispatch = useDispatch();
  const toDos = useSelector((store) => store.toDos);

  return (
    <div className={classes.todosBox}>
      <Add />
      {toDos.map((item, index) => {
        return <ToDo key={item.id} data={item} />;
      })}
    </div>
  );
};

export default ToDos;
