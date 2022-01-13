import axios from "axios";

export function SignUp(email, name, nickname, password) {
    let webUrl = "http://localhost:8080";

    function request() {
        const data = {
            email: email,
            name: name,
            nickname: nickname,
            password: password
        };

        axios.post(webUrl + "/api/sign/signup", data)
            .then(response => {
                alert(response.status + " 성공적입니다.")
            }).catch(error => {
                console.log("요청한 data: " + data.email + ", " + data.name + ", " + data.nickname + ", " + data.password)
                alert(error);
        });
    }
    request();
}

export default SignUp;