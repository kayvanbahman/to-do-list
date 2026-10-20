import Document, { Html, Head, Main, NextScript } from "next/document";

const themeScript = `(function(){try{var saved=localStorage.getItem('todo-theme');var theme=saved==='light'||saved==='dark'?saved:window.matchMedia('(prefers-color-scheme: dark)').matches?'dark':'light';document.documentElement.dataset.theme=theme}catch(e){document.documentElement.dataset.theme='light'}})();`;

export default class MyDocument extends Document {
  render() {
    return (
      <Html lang="en">
        <Head />
        <body>
          <script dangerouslySetInnerHTML={{ __html: themeScript }} />
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}
