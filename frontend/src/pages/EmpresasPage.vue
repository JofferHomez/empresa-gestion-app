<template>
  <q-page padding>
    <div class="row items-center q-mb-md">
      <div class="col">
        <div class="text-h5">Empresas</div>
      </div>
      <div class="col-auto" v-if="auth.isAdmin">
        <q-btn label="Nueva empresa" color="primary" @click="openCreate" />
      </div>
    </div>

    <q-table
      :rows="empresas"
      :columns="columns"
      row-key="nit"
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
          <div class="text-h6">{{ isEdit ? 'Editar empresa' : 'Nueva empresa' }}</div>
        </q-card-section>
        <q-card-section>
          <EmpresaForm v-model="current" />
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
import empresaService from '../services/empresaService'
import EmpresaForm from '../components/EmpresaForm.vue'
import { useAuthStore } from '../stores/authStore'

const $q = useQuasar()
const auth = useAuthStore()

const empresas = ref([])
const showDialog = ref(false)
const isEdit = ref(false)
const current = ref({ nit: '', nombre: '', direccion: '', telefono: '' })

const columns = [
  { name: 'nit', label: 'NIT', field: 'nit', align: 'left' },
  { name: 'nombre', label: 'Nombre', field: 'nombre', align: 'left' },
  { name: 'direccion', label: 'Dirección', field: 'direccion', align: 'left' },
  { name: 'telefono', label: 'Teléfono', field: 'telefono', align: 'left' },
  { name: 'actions', label: 'Acciones', field: 'actions', align: 'right' }
]

const load = async () => {
  empresas.value = await empresaService.list()
}

const openCreate = () => {
  isEdit.value = false
  current.value = { nit: '', nombre: '', direccion: '', telefono: '' }
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
      await empresaService.update(current.value.nit, current.value)
    } else {
      await empresaService.create(current.value)
    }
    showDialog.value = false
    await load()
    $q.notify({ type: 'positive', message: 'Guardado correctamente' })
  } catch (e) {
    $q.notify({ type: 'negative', message: 'Error al guardar' })
  }
}

const remove = async (row) => {
  $q.dialog({
    title: 'Confirmar',
    message: `¿Eliminar empresa ${row.nombre}?`,
    cancel: true,
    ok: 'Eliminar'
  }).onOk(async () => {
    try {
      await empresaService.remove(row.nit)
      await load()
      $q.notify({ type: 'positive', message: 'Eliminada' })
    } catch (e) {
      $q.notify({ type: 'negative', message: 'Error al eliminar' })
    }
  })
}

const view = (row) => {
  $q.dialog({
    title: 'Detalle empresa',
    message: `NIT: ${row.nit}\nNombre: ${row.nombre}\nDirección: ${row.direccion}\nTeléfono: ${row.telefono}`
  })
}

onMounted(load)
</script>
