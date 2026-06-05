<template>
  <q-page padding>
    <div class="row items-center q-mb-md">
      <div class="col">
        <div class="text-h5">Órdenes</div>
      </div>
      <div class="col-auto" v-if="auth.isAdmin">
        <q-btn label="Nueva orden" color="primary" @click="openCreate" />
      </div>
    </div>

    <q-table
      :rows="ordenes"
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
      <q-card style="min-width: 600px">
        <q-card-section>
          <div class="text-h6">{{ isEdit ? 'Editar orden' : 'Nueva orden' }}</div>
        </q-card-section>
        <q-card-section>
          <OrdenForm
            v-model="current"
            :empresas-options="empresas"
            :clientes-options="clientes"
          />
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
import ordenService from '../services/ordenService'
import empresaService from '../services/empresaService'
import clienteService from '../services/clienteService'
import OrdenForm from '../components/OrdenForm.vue'
import { useAuthStore } from '../stores/authStore'

const $q = useQuasar()
const auth = useAuthStore()

const ordenes = ref([])
const empresas = ref([])
const clientes = ref([])

const showDialog = ref(false)
const isEdit = ref(false)
const current = ref({ id: null, empresaId: null, clienteId: null, total: 0 })

const columns = [
  { name: 'id', label: 'ID', field: 'id', align: 'left' },
  { name: 'empresa', label: 'Empresa', field: row => row.empresaNombre, align: 'left' },
  { name: 'cliente', label: 'Cliente', field: row => row.clienteNombre, align: 'left' },
  { name: 'total', label: 'Total', field: 'total', align: 'right' },
  { name: 'actions', label: 'Acciones', field: 'actions', align: 'right' }
]

const load = async () => {
  ordenes.value = await ordenService.list()
  empresas.value = await empresaService.list()
  clientes.value = await clienteService.list()
}

const openCreate = () => {
  isEdit.value = false
  current.value = { id: null, empresaId: null, clienteId: null, total: 0 }
  showDialog.value = true
}

const edit = (row) => {
  isEdit.value = true
  current.value = {
    id: row.id,
    empresaId: row.empresaId,
    clienteId: row.clienteId,
    total: row.total
  }
  showDialog.value = true
}

const save = async () => {
  try {
    if (isEdit.value) {
      await ordenService.update(current.value.id, current.value)
    } else {
      await ordenService.create(current.value)
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
    message: `¿Eliminar orden #${row.id}?`,
    cancel: true,
    ok: 'Eliminar'
  }).onOk(async () => {
    try {
      await ordenService.remove(row.id)
      await load()
      $q.notify({ type: 'positive', message: 'Eliminada' })
    } catch {
      $q.notify({ type: 'negative', message: 'Error al eliminar' })
    }
  })
}

const view = (row) => {
  $q.dialog({
    title: 'Detalle orden',
    message: `ID: ${row.id}\nEmpresa: ${row.empresaNombre}\nCliente: ${row.clienteNombre}\nTotal: ${row.total}`
  })
}

onMounted(load)
</script>
