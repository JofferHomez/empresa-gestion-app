<template>
  <q-form>
    <q-input v-model="model.nombre" label="Nombre" dense />
    <q-input v-model="model.email" label="Email" type="email" dense class="q-mt-md" />
    <q-input v-model="model.telefono" label="Teléfono" dense class="q-mt-md" />
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
