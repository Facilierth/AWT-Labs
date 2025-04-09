import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import NotFound from '@/pages/NotFound.vue'
import AuthorPage from "@/pages/AuthorPage.vue";

const routes = [
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/:pathMatch(.*)*',
        component: NotFound
    },
    {
        path: '/authors',
        component: AuthorPage
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
