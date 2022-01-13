import axios from "axios";

export function Login(email, password) {
    let webUrl = "http://localhost:8080";

    function request() {
        const data = {
            email: email,
            password: password
        };

        axios.post(webUrl + "/api/sign/login", data)
            .then(response => {
                axios.defaults.headers.common['X-AUTH-TOKEN'] = response.data.data.accessToken;

                alert(response.status + " 성공적입니다.")
            }).catch(error => {
            console.log(data.email + ", " + data.password)
            alert(error);
        });
    }
    request();
}

export default Login;