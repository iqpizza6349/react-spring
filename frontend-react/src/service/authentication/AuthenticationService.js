import axios from "axios";

class AuthenticationService {

    // nickname, password 등 회원가입 시 요구되는 parameters 서버로 보냄
    executeAuthenticationService(email, name, nickname, password) {
        return axios.post("http://localhost:8080/api/sign/signup", {
            email,
            name,
            nickname,
            password
        });
    }

    execute







}


