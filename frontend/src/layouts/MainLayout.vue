<template>
  <q-layout view="hHh lpR fFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>Empresa Gestión App</q-toolbar-title>
        <q-btn flat icon="logout" @click="logout" />
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above v-model="leftDrawerOpen" bordered>
      <q-list>
        <q-item clickable to="/empresas">
          <q-item-section avatar><q-icon name="business" /></q-item-section>
          <q-item-section>Empresas</q-item-section>
        </q-item>
        <q-item clickable to="/productos">
          <q-item-section avatar><q-icon name="inventory_2" /></q-item-section>
          <q-item-section>Productos</q-item-section>
        </q-item>
        <q-item clickable to="/inventario">
          <q-item-section avatar><q-icon name="list_alt" /></q-item-section>
          <q-item-section>Inventario</q-item-section>
        </q-item>
        <q-item clickable to="/clientes">
          <q-item-section avatar><q-icon name="people" /></q-item-section>
          <q-item-section>Clientes</q-item-section>
        </q-item>
        <q-item clickable to="/ordenes">
          <q-item-section avatar><q-icon name="receipt_long" /></q-item-section>
          <q-item-section>Órdenes</q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'

const leftDrawerOpen = ref(true)
const auth = useAuthStore()
const router = useRouter()

const logout = () => {
  auth.logout()
  router.push({ name: 'login' })
}
</script>
