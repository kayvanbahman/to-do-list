import { MdDarkMode, MdLightMode, MdLogout } from "react-icons/md";
import { useTheme } from "../utils/theme";
import classes from "../styles/container.module.sass";

export default function AppHeader({ onLogout }) {
  const { theme, toggleTheme } = useTheme();

  return (
    <header className={classes.topbar}>
      <div className={classes.brand}>
        <span className={classes.brandMark} aria-hidden="true">✓</span>
        <span>Todo<span className={classes.brandAccent}>.</span></span>
      </div>
      <div className={classes.topActions}>
        <button className={classes.iconButton} type="button" onClick={toggleTheme}
          aria-label={theme === "dark" ? "Switch to light mode" : "Switch to dark mode"}
          title={theme === "dark" ? "Light mode" : "Dark mode"}>
          {theme === "dark" ? <MdLightMode aria-hidden="true" /> : <MdDarkMode aria-hidden="true" />}
        </button>
        {onLogout && (
          <button className={classes.signOut} type="button" onClick={onLogout}>
            <MdLogout aria-hidden="true" />
            <span>Sign out</span>
          </button>
        )}
      </div>
    </header>
  );
}
