<template>
  <q-page padding>
    <div class="row items-center q-mb-md">
      <div class="col">
        <div class="text-h5">Productos</div>
      </div>
      <div class="col-auto" v-if="auth.isAdmin">
        <q-btn label="Nuevo producto" color="primary" @click="openCreate" />
      </div>
    </div>

    <q-table
      :rows="productos"
      :columns="columns"
      row-key="id"
      flat
      bordered
    >
      <template #body-cell-actions="props">
        <q-td>
          <q-btn dense flat icon="visibility" @click="view(props.row)" />
          <q-btn dense flat icon="edit" v-if="auth.isAdmin" @click="edit(props.row)" />
          <q-btn dense flat icon="delete" color="negative" v-if="auth.isAdmin" @click="remove(props.row)" />
        </q-td>
      </template>
    </q-table>

    <q-dialog v-model="showDialog">
      <q-card style="min-width: 500px">
        <q-card-section>
          <div class="text-h6">{{ isEdit ? 'Editar producto' : 'Nuevo producto' }}</div>
        </q-card-section>
        <q-card-section>
          <ProductoForm v-model="current" />
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat label="Cancelar" v-close-popup />
          <q-btn label="Guardar" color="primary" @click="save" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useQuasar } from 'quasar'
import productoService from '../services/productoService'
import ProductoForm from '../components/ProductoForm.vue'
import { useAuthStore } from '../stores/authStore'

const $q = useQuasar()
const auth = useAuthStore()

const productos = ref([])
const showDialog = ref(false)
const isEdit = ref(false)
const current = ref({ id: null, nombre: '', descripcion: '', precio: 0 })

const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'descripcion', label: 'Descripción', field: 'descripcion', align: 'left' },
  { name: 'precio', label: 'Precio', field: 'precio', align: 'right' },
  { name: 'actions', label: 'Acciones', field: 'actions', align: 'right' }
]

const load = async () => {
  productos.value = await productoService.list()
}

const openCreate = () => {
  isEdit.value = false
  current.value = { id: null, nombre: '', descripcion: '', precio: 0 }
  showDialog.value = true
}

const edit = (row) => {
  isEdit.value = true
  current.value = { ...row }
  showDialog.value = true
}

const save = async () => {
  try {
    if (isEdit.value) {
      await productoService.update(current.value.id, current.value)
    } else {
      await productoService.create(current.value)
    }
    showDialog.value = false
    await load()
    $q.notify({ type: 'positive', message: 'Guardado correctamente' })
  } catch {
    $q.notify({ type: 'negative', message: 'Error al guardar' })
  }
}

const remove = async (row) => {
  $q.dialog({
    title: 'Confirmar',
    message: `¿Eliminar producto ${row.nombre}?`,
    cancel: true,
    ok: 'Eliminar'
  }).onOk(async () => {
    try {
      await productoService.remove(row.id)
      await load()
      $q.notify({ type: 'positive', message: 'Eliminado' })
    } catch {
      $q.notify({ type: 'negative', message: 'Error al eliminar' })
    }
  })
}

const view = (row) => {
  $q.dialog({
    title: 'Detalle producto',
    message: `Nombre: ${row.nombre}\nDescripción: ${row.descripcion}\nPrecio: ${row.precio}`
  })
}

onMounted(load)
</script>
