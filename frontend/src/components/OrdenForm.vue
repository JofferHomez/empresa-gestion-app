<template>
  <q-form>
    <q-select
      v-model="model.empresaId"
      :options="empresasOptions"
      option-value="id"
      option-label="nombre"
      label="Empresa"
      dense
    />
    <q-select
      v-model="model.clienteId"
      :options="clientesOptions"
      option-value="id"
      option-label="nombre"
      label="Cliente"
      dense
      class="q-mt-md"
    />
    <q-input
      v-model.number="model.total"
      label="Total"
      type="number"
      dense
      class="q-mt-md"
    />
    <!-- Aquí podrías agregar selección de productos y cantidades -->
  </q-form>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: { type: Object, required: true },
  empresasOptions: { type: Array, default: () => [] },
  clientesOptions: { type: Array, default: () => [] }
})
const emit = defineEmits(['update:modelValue'])

const model = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
