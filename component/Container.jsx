import React from "react";
import Todos from "./ToDos";
import classes from "../styles/container.module.sass";
import Filters from "./Filters";
import { useSelector } from "react-redux";
import AppHeader from "./AppHeader";
const Container = ({ onLogout }) => {
  const toDos = useSelector((store) => store.toDos);
  const error = useSelector((store) => store.error);
  const activeCount = toDos.filter((item) => !item.done).length;

  return (
    <main className={classes.page}>
      <div className={classes.shell}>
        <AppHeader onLogout={onLogout} />
        <section className={classes.intro} aria-labelledby="page-title">
          <div>
            <p className={classes.eyebrow}>YOUR SPACE TO FOCUS</p>
            <h1 id="page-title">Make room for <span>what matters.</span></h1>
            <p className={classes.subtitle}>One clear place for everything you need to get done.</p>
          </div>
          <div className={classes.countCard} aria-live="polite">
            <span className={classes.countNumber}>{activeCount}</span>
            <span>{activeCount === 1 ? "task left" : "tasks left"}</span>
          </div>
        </section>
        <section className={classes.panel} aria-label="Your tasks">
          <div className={classes.panelHeading}>
            <div>
              <p className={classes.sectionEyebrow}>THE LIST</p>
              <h2>Your tasks</h2>
            </div>
            <span className={classes.panelBadge}>{activeCount} active</span>
          </div>
          <Todos />
          {error && <p className={classes.error} role="alert">{error}</p>}
          <Filters activeCount={activeCount} />
        </section>
        <p className={classes.footer}>Small steps, steady progress.</p>
      </div>
    </main>
  );
};

export default Container;
