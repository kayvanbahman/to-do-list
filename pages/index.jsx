import Head from "next/head";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import Container from "../component/Container";
import Axios from "../utils/request";
import AuthForm from "../component/AuthForm";
import classes from "../styles/container.module.sass";
import { GET_LIST } from "../redux/types";
import AppHeader from "../component/AppHeader";

export default function Home() {
  const [status, setStatus] = useState("loading");
  const dispatch = useDispatch();

  useEffect(() => {
    const unauthorized = () => {
      dispatch({ type: GET_LIST, payload: [] });
      setStatus("guest");
    };
    window.addEventListener("todo:unauthorized", unauthorized);
    Axios.get("/todos")
      .then(() => setStatus("authenticated"))
      .catch((error) => setStatus(error.response?.status === 401 ? "guest" : "error"));
    return () => window.removeEventListener("todo:unauthorized", unauthorized);
  }, [dispatch]);

  const logout = async () => {
    await Axios.post("/auth/logout").catch(() => {});
    dispatch({ type: GET_LIST, payload: [] });
    setStatus("guest");
  };

  return (
    <div>
      <Head>
        <title>Todo — make room for what matters</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" key="viewport" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      {status === "authenticated" ? (
        <Container onLogout={logout} />
      ) : status === "guest" ? (
        <AuthForm onSuccess={() => {
          dispatch({ type: GET_LIST, payload: [] });
          setStatus("authenticated");
        }} />
      ) : (
        <main className={classes.page}>
          <div className={classes.shell}>
            <AppHeader />
            <div className={classes.stateCard}>
              <span className={classes.brandMark} aria-hidden="true">✓</span>
              <h1>{status === "loading" ? "Getting your space ready…" : "We couldn't reach your tasks"}</h1>
              <p>{status === "loading" ? "Just a moment." : "Check that the backend is running, then try again."}</p>
              {status === "error" && <button type="button" onClick={() => window.location.reload()}>Try again</button>}
            </div>
          </div>
        </main>
      )}
    </div>
  );
}
