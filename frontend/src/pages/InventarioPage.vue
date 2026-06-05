<template>
  <q-page padding>
    <div class="row items-center q-mb-md">
      <div class="col">
        <div class="text-h5">Inventario</div>
      </div>
      <div class="col-auto">
        <q-btn label="Descargar PDF" icon="picture_as_pdf" color="primary" class="q-mr-sm" @click="downloadPdf" />
        <q-btn label="Enviar por correo" icon="email" color="secondary" @click="openEmailDialog" />
      </div>
    </div>

    <q-table :rows="items" :columns="columns" row-key="id" />

    <q-dialog v-model="showEmailDialog">
      <q-card style="min-width: 400px">
        <q-card-section>
          <div class="text-h6">Enviar inventario por correo</div>
        </q-card-section>
        <q-card-section>
          <q-input v-model="email" label="Email destino" type="email" dense />
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat label="Cancelar" v-close-popup />
          <q-btn label="Enviar" color="primary" @click="sendEmail" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useQuasar } from 'quasar'
import inventarioService from '../services/inventarioService'

const $q = useQuasar()
const items = ref([])
const showEmailDialog = ref(false)
const email = ref('')

const columns = [
  { name: 'producto', label: 'Producto', field: row => row.productoNombre, align: 'left' },
  { name: 'empresa', label: 'Empresa', field: row => row.empresaNombre, align: 'left' },
  { name: 'cantidad', label: 'Cantidad', field: 'cantidad', align: 'right' }
]

const load = async () => {
  items.value = await inventarioService.list()
}

const downloadPdf = async () => {
  try {
    const blob = await inventarioService.descargarPdf()
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = 'inventario.pdf'
    a.click()
    window.URL.revokeObjectURL(url)
  } catch (e) {
    $q.notify({ type: 'negative', message: 'Error al descargar PDF' })
  }
}

const openEmailDialog = () => {
  email.value = ''
  showEmailDialog.value = true
}

const sendEmail = async () => {
  try {
    await inventarioService.enviarCorreo({ email: email.value })
    showEmailDialog.value = false
    $q.notify({ type: 'positive', message: 'Correo enviado' })
  } catch (e) {
    $q.notify({ type: 'negative', message: 'Error al enviar correo' })
  }
}

onMounted(load)
</script>
