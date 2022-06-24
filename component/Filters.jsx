import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { getList, removeCompleted } from "../redux/action";
import classes from "../styles/filters.module.sass";
const Filters = (props) => {
  const [filter, setFilter] = useState();
  const router = useRouter();
  const filterHandler = (type) => {
    setFilter(type);
    router.push(`/?filter=${type}`, undefined, { shallow: true });
    dispatch(getList(type));
  };
  useEffect(() => {
    if (router.query.filter) setFilter(router.query?.filter);
    else setFilter("all");
  }, []);
  const dispatch = useDispatch();
  return (
    <div className={classes.filtersContainer}>
      <span className={classes.countText}>
        <span>{props.activeCount}</span> items left
      </span>
      <div className={classes.filters}>
        <span
          onClick={() => filterHandler("all")}
          className={filter === "all" ? classes.active : null}
        >
          All
        </span>
        <span
          className={filter === "active" ? classes.active : null}
          onClick={() => filterHandler("active")}
        >
          Active
        </span>
      </div>
      <span
        onClick={() => dispatch(removeCompleted())}
        className={classes.clearBtn}
      >
        Clear completed
      </span>
    </div>
  );
};

export default Filters;
