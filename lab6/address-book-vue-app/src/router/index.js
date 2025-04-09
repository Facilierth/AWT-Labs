import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../App.vue'
import NotFound from '@/pages/NotFound.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        component: HomePage
    },
    {
        path: '*',
        component: NotFound
    }
]

export default new VueRouter({
    mode: 'history',
    routes
})
