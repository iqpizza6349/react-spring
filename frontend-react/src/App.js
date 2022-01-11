import './App.css';
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
  // 요청 받은 정보를 담아줄 변수 선언
  const [testStr, setTestStr] = useState('');

  // 변수 초기화
  function callBack(str) {
    setTestStr(str);
  }

  // 첫 번째 렌더링을 마친 후 실행
  useEffect(
      () => {
        axios({
          url: '/home',
          method: 'GET'
        }).then((res) => {
          callBack(res.data);
        })
      }, []
  );

  return (
      <div className="App">
        <header className="App-header">
          {testStr}
        </header>
      </div>
  );
}

export default App;
