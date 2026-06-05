<template>
  <q-page padding>
    <div class="row items-center q-mb-md">
      <div class="col">
        <div class="text-h5">Clientes</div>
      </div>
      <div class="col-auto" v-if="auth.isAdmin">
        <q-btn label="Nuevo cliente" color="primary" @click="openCreate" />
      </div>
    </div>

    <q-table
      :rows="clientes"
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
          <div class="text-h6">{{ isEdit ? 'Editar cliente' : 'Nuevo cliente' }}</div>
        </q-card-section>
        <q-card-section>
          <ClienteForm v-model="current" />
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
import clienteService from '../services/clienteService'
import ClienteForm from '../components/ClienteForm.vue'
import { useAuthStore } from '../stores/authStore'

const $q = useQuasar()
const auth = useAuthStore()

const clientes = ref([])
const showDialog = ref(false)
const isEdit = ref(false)
const current = ref({ id: null, nombre: '', email: '', telefono: '' })

const columns = [
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'email', label: 'Email', field: 'email', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'actions', label: 'Acciones', field: 'actions', align: 'right' }
]

const load = async () => {
  clientes.value = await clienteService.list()
}

const openCreate = () => {
  isEdit.value = false
  current.value = { id: null, nombre: '', email: '', telefono: '' }
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
      await clienteService.update(current.value.id, current.value)
    } else {
      await clienteService.create(current.value)
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
    message: `¿Eliminar cliente ${row.nombre}?`,
    cancel: true,
    ok: 'Eliminar'
  }).onOk(async () => {
    try {
      await clienteService.remove(row.id)
      await load()
      $q.notify({ type: 'positive', message: 'Eliminado' })
    } catch {
      $q.notify({ type: 'negative', message: 'Error al eliminar' })
    }
  })
}

const view = (row) => {
  $q.dialog({
    title: 'Detalle cliente',
    message: `Nombre: ${row.nombre}\nEmail: ${row.email}\nTeléfono: ${row.telefono}`
  })
}

onMounted(load)
</script>
