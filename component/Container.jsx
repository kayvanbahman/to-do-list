import React from "react";
import Todos from "./ToDos";
import classes from "../styles/container.module.sass";
import Filters from "./Filters";
import { useSelector } from "react-redux";
const Container = () => {
  const toDos = useSelector((store) => store.toDos);

  return (
    <div className={classes.container}>
      <h1>Todo</h1>
      <Todos />
      <Filters activeCount={toDos.filter((it) => !it.done)?.length} />
    </div>
  );
};

export default Container;
