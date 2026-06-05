import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import MainLayout from '../layouts/MainLayout.vue'
import LoginPage from '../pages/LoginPage.vue'
import EmpresasPage from '../pages/EmpresasPage.vue'
import ProductosPage from '../pages/ProductosPage.vue'
import InventarioPage from '../pages/InventarioPage.vue'
import ClientesPage from '../pages/ClientesPage.vue'
import OrdenesPage from '../pages/OrdenesPage.vue'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage,
    meta: { public: true }
  },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', redirect: '/empresas' },
      { path: 'empresas', name: 'empresas', component: EmpresasPage },
      { path: 'productos', name: 'productos', component: ProductosPage },
      { path: 'inventario', name: 'inventario', component: InventarioPage },
      { path: 'clientes', name: 'clientes', component: ClientesPage },
      { path: 'ordenes', name: 'ordenes', component: OrdenesPage },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()
  if (!to.meta.public && !auth.isAuthenticated) {
    return next({ name: 'login' })
  }
  next()
})

export default router
