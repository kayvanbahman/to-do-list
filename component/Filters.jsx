import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { getList, removeCompleted } from "../redux/action";
import classes from "../styles/filters.module.sass";
const Filters = (props) => {
  const [filter, setFilter] = useState();
  const router = useRouter();
  const dispatch = useDispatch();
  const filterHandler = (type) => {
    if (type === filter) return;
    router.push(`/?filter=${type}`, undefined, { shallow: true });
  };
  useEffect(() => {
    if (!router.isReady) return;

    const nextFilter = router.query.filter === "active" ? "active" : "all";
    setFilter(nextFilter);
    dispatch(getList(nextFilter)).catch(() => {});
  }, [dispatch, router.isReady, router.query.filter]);
  return (
    <div className={classes.filtersContainer}>
      <span className={classes.countText}>
        <strong>{props.activeCount}</strong> {props.activeCount === 1 ? "task" : "tasks"} left
      </span>
      <div className={classes.filters} role="group" aria-label="Filter tasks">
        <button type="button"
          onClick={() => filterHandler("all")}
          className={filter === "all" ? classes.active : null}
          aria-pressed={filter === "all"}
        >
          All
        </button>
        <button type="button"
          className={filter === "active" ? classes.active : null}
          onClick={() => filterHandler("active")}
          aria-pressed={filter === "active"}
        >
          Active
        </button>
      </div>
      <button type="button"
        onClick={() => dispatch(removeCompleted(filter)).catch(() => {})}
        className={classes.clearBtn}
      >
        Clear completed
      </button>
    </div>
  );
};

export default Filters;
