import React, {useState} from "react";
import SignUp from "../request/SignUp";
import Login from "../request/Login";
import Home from "../request/Home";

function InputSample() {

    /*
    input에 작성하고
    button을 눌르면 SignSample에서 axios으로 Post하여 회원가입하는 로직
    로그인도 마찬가지
    토큰으로 Home.js Response까지 가져오는 게 목표
    토큰은 헤더에 저장
     */

    const [email, setEmail] = useState("");                 // email
    const [name, setName] = useState("");                   // name
    const [nickname, setNickname] = useState("");           // nickname
    const [password, setPassword] = useState("");           // password
    const [loginEmail, setLoginEmail] = useState("");       // 로그인 시 사용할 email
    const [loginPassword, setLoginPassword] = useState(""); // 로그인 시 사용할 password

    const inputSignUpEmail = (e) => {
        setEmail(e.target.value);
    };

    const inputLoginEmail = (e) => {
        setLoginEmail(e.target.value);
    }

    const inputName = (e) => {
        setName(e.target.value);
    }

    const inputNickname = (e) => {
        setNickname(e.target.value);
    }

    const inputSignUpPassword = (e) => {
        setPassword(e.target.value);
    }

    const inputLoginPassword = (e) => {
        setLoginPassword(e.target.value);
    }

    const onSignRequest = () => {
        SignUp(email, name, nickname, password);
    }

    const onLoginRequest = () => {
        Login(loginEmail, loginPassword);
    }

    const onHome = () => {
        Home()
            .then(response => {
                alert(response.data.toString())
            }).catch(error => {
                alert(error)
        });
    }

    const onReset = () => {
        setEmail("");
        setName("");
        setNickname("");
        setPassword("");
    };

    return (
        <div>
            <div>
                이메일
                <input onChange={inputSignUpEmail} value={email}/>
            </div>
            <div>
                이름
                <input onChange={inputName} value={name}/>
            </div>
            <div>
                닉네임
                <input onChange={inputNickname} value={nickname}/>
            </div>
            <div>
                비밀번호
                <input onChange={inputSignUpPassword} value={password}/>
            </div>
            <button onClick={onSignRequest}>회원가입</button>
            <b/>
            <div>
                <div>
                    이메일
                    <input onChange={inputLoginEmail} value={loginEmail}/>
                </div>
                <div>
                    비밀번호
                    <input onChange={inputLoginPassword} value={loginPassword}/>
                </div>
                <button onClick={onLoginRequest}>로그인</button>
            </div>
            <b/>
            <div>
                <button onClick={onHome}>토큰 테스트</button>
            </div>
            <b/>
            <div>
                <button onClick={onReset}>초기화</button>
            </div>
        </div>
    );
}

export default InputSample;