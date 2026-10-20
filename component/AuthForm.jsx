import { useState } from "react";
import Axios, { errorMessage } from "../utils/request";
import containerClasses from "../styles/container.module.sass";
import classes from "../styles/auth.module.sass";
import AppHeader from "./AppHeader";

export default function AuthForm({ onSuccess }) {
  const [mode, setMode] = useState("login");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [pending, setPending] = useState(false);

  const submit = async (event) => {
    event.preventDefault();
    setError("");
    setPending(true);
    try {
      await Axios.post(`/auth/${mode}`, { email, password });
      onSuccess();
    } catch (requestError) {
      setError(errorMessage(requestError));
    } finally {
      setPending(false);
    }
  };

  return (
    <main className={containerClasses.page}>
      <div className={containerClasses.shell}>
        <AppHeader />
        <div className={classes.layout}>
          <div className={classes.intro}>
            <p className={containerClasses.eyebrow}>A LITTLE MORE CLARITY, EVERY DAY</p>
            <h1>Less clutter.<br /><span>More momentum.</span></h1>
            <p>Keep your plans in one calm space. Focus on the next step, and let the rest wait.</p>
            <div className={classes.note}><span aria-hidden="true">✦</span> A simple place to make progress.</div>
          </div>
          <div className={classes.card}>
            <span className={classes.cardMark} aria-hidden="true">✓</span>
            <p className={classes.cardEyebrow}>{mode === "login" ? "WELCOME BACK" : "GET STARTED"}</p>
            <h2>{mode === "login" ? "Sign in to your space" : "Create your account"}</h2>
            <p className={classes.description}>{mode === "login" ? "Your tasks are right where you left them." : "A clearer day starts with one small step."}</p>
            <form className={classes.form} onSubmit={submit}>
              <label>Email address
                <input type="email" placeholder="you@example.com" value={email}
                  onChange={(event) => setEmail(event.target.value)} required autoComplete="email" />
              </label>
              <label>Password
                <input type="password" placeholder="Enter your password" value={password}
                  onChange={(event) => setPassword(event.target.value)} required minLength={mode === "register" ? 8 : 1}
                  autoComplete={mode === "login" ? "current-password" : "new-password"} />
              </label>
              {error && <p className={classes.error} role="alert">{error}</p>}
              <button className={classes.submit} type="submit" disabled={pending}>
                {pending ? "Please wait…" : mode === "login" ? "Sign in" : "Create account"}
              </button>
            </form>
            <div className={classes.switchRow}>
              <span>{mode === "login" ? "New here?" : "Already have an account?"}</span>
              <button type="button" onClick={() => { setMode(mode === "login" ? "register" : "login"); setError(""); }}>
                {mode === "login" ? "Create an account" : "Sign in"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
