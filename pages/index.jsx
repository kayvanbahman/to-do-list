import Head from "next/head";
import Container from "../component/Container";
import Axios from "../utils/request";
import { wrapper } from "../redux/store";
import { GET_LIST } from "../redux/types";

export default function Home() {
  return (
    <div>
      <Head>
        <title>To-Do</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Container />
    </div>
  );
}
export const getServerSideProps = wrapper.getServerSideProps(
  (store) => async (ctx) => {
    try {
      const data = await Axios.get(
        "/ToDos" + (ctx.query?.filter === "active" ? `/?done=false` : "")
      );

      store.dispatch({ type: GET_LIST, payload: data.data });
      return {
        props: {},
      };
    } catch (e) {
      console.log(e);
    }
  }
);
