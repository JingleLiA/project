// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/main.css'
import echarts from 'echarts'
import 'echarts/theme/macarons.js';
import VueResource from 'vue-resource'


Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$echarts = echarts ;
Vue.use(VueResource);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
