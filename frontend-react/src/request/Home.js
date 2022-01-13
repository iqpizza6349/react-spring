const axios = require("axios");

export function Home() {
    return axios.get("http://localhost:3000/home");
}

export default Home;