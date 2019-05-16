<template>
  <div>
    <div class="login-wrap note" v-show="showLogin" :style="note">
      <h3>登录</h3>
      <p v-show="showTishi">{{tishi}}</p>
      <input type="text" placeholder="请输入用户名" v-model="username">
      <input type="password" placeholder="请输入密码" v-model="password">
      <button v-on:click="login">登录</button>
    </div>
  </div>
</template>

<script>
  import conf from '../conf.js'
  import {setCookie, getCookie} from '../assets/js/cookie.js'

  export default {
    data() {
      return {
        username: '',
        password: '',
        newUsername: '',
        newPassword: '',
        tishi: '',
        showTishi: false,
        showLogin: true,
        showRegister: false,
        note: {
          backgroundImage: "url(" + require("../assets/bg5.jpg") + ")",
        }
      }
    },
    mounted() {
      document.getElementsByClassName('navs')[0].style.display = "none";
      if (getCookie('username')) {
        this.$router.push('/')
      }
    },
    methods: {
      login() {
        if (this.username == "" || this.password == "") {
          alert("请输入用户名或密码")
        } else {
          let data = {'username': this.username, 'password': this.password}
          this.$http.post(`${conf.ROOT}/user/login`, data).then((res) => {
            console.log(res.body);
            if (res.body.data == '0') {
              this.tishi = "账号或密码输入错误"
              this.showTishi = true
            } else {

              this.tishi = "登录成功";
              this.showTishi = true;
              this.$router.push({path: '/', query: {id: 1}});
              /*setCookie('username', this.username, 1000 * 60);
              setTimeout(function () {
                this.$router.push({path: '/', query: {id: 1}});
              }.bind(this), 1000)*/
            }
          })
        }
      },
      ToRegister() {
        this.showRegister = true
        this.showLogin = false
      },
      ToLogin() {
        this.showRegister = false
        this.showLogin = true
      },
      register() {
        if (this.newUsername == "" || this.newPassword == "") {
          alert("请输入用户名或密码")
        } else {
          let data = {'username': this.newUsername, 'password': this.newPassword}
          this.$http.post('http://localhost/vueapi/index.php/Home/user/register', data).then((res) => {
            console.log(res)
            if (res.data == "ok") {
              this.tishi = "注册成功"
              this.showTishi = true
              this.username = ''
              this.password = ''
              setTimeout(function () {
                this.showRegister = false
                this.showLogin = true
                this.showTishi = false
              }.bind(this), 1000)
            }
          })
        }
      }
    }
  }
</script>

<style>

  .login-wrap {
    text-align: center;
  }

  input {
    display: block;
    width: 250px;
    height: 40px;
    line-height: 40px;
    margin: 0 auto;
    margin-bottom: 10px;
    outline: none;
    border: 1px solid #888;
    padding: 10px;
    box-sizing: border-box;
  }

  p {
    color: red;
  }

  button {
    display: block;
    width: 250px;
    height: 40px;
    line-height: 40px;
    margin: 0 auto;
    border: none;
    background-color: #41b883;
    color: #fff;
    font-size: 16px;
    margin-bottom: 5px;
  }

  span {
    cursor: pointer;
  }

  span:hover {
    color: #41b883;
  }

  .note {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    min-width: 1000px;
    z-index: -10;
    zoom: 1;
    background-color: #fff;
    background-repeat: no-repeat;
    background-size: cover;
    -webkit-background-size: cover;
    -o-background-size: cover;
    background-position: center 0;
  }

  .navs {
    display: none;
  }
</style>

