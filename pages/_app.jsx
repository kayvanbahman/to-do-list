import React from "react";
import { wrapper } from "../redux/store";
import { ThemeProvider } from "../utils/theme";
import "../styles/globals.sass";

function MyApp({ Component, pageProps }) {
  return <ThemeProvider><Component {...pageProps} /></ThemeProvider>;
}
export default wrapper.withRedux(MyApp);
