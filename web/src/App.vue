<template>
  <div id="app">
    <header id="">
      <div>
        <router-link to="/">VAVE TECH 树径测量</router-link>
      </div>
      <div class="navs">
        <router-link to="/">测量记录</router-link>
        <router-link to="/tree">树木成长记录</router-link>
      </div>
    </header>
    <div class="router-outer">
      <router-view v-if="isRouterAlive"></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide(){
    return{
      reload: this.reload
    }
  },
  data() {
    return {
      isRouterAlive: true
    }
  },
  methods:{
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function () {
        this.isRouterAlive = true;
      })
    }
  }
}

</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color:#333333;
}
header{
  height:30px;
  padding:10px 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  vertical-align: -webkit-baseline-middle;
  box-shadow: 0 1px 1px 1px rgba(0,0,0,0.1);
}
header div{
  font-size:14px;
}
header a{
  color:#333333;
  text-decoration: none;
}
.navs a{
  margin-left:15px;
}
header .navs a:hover{
  text-decoration: underline;
}
.router-outer{
  padding:20px;
}
.navs{
  font-size:12px;
}
</style>
