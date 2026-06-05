<template>
  <q-form>
    <q-input v-model="model.nombre" label="Nombre" dense />
    <q-input v-model="model.descripcion" label="Descripción" dense class="q-mt-md" />
    <q-input v-model.number="model.precio" label="Precio" type="number" dense class="q-mt-md" />
  </q-form>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: { type: Object, required: true }
})
const emit = defineEmits(['update:modelValue'])

const model = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
