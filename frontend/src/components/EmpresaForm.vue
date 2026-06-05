<template>
  <q-form>
    <q-input v-model="model.nit" label="NIT" dense />
    <q-input v-model="model.nombre" label="Nombre" dense class="q-mt-md" />
    <q-input v-model="model.direccion" label="Dirección" dense class="q-mt-md" />
    <q-input v-model="model.telefono" label="Teléfono" dense class="q-mt-md" />
  </q-form>
</template>

<script setup>
const props = defineProps({
  modelValue: { type: Object, required: true }
})
const emit = defineEmits(['update:modelValue'])

const model = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})
</script>
