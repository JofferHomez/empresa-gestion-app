<template>
  <q-page class="flex flex-center">
    <q-card style="width: 400px">
      <q-card-section>
        <div class="text-h6">Iniciar sesión</div>
      </q-card-section>
      <q-card-section>
        <q-form @submit.prevent="onSubmit">
          <q-input v-model="email" label="Email" type="email" dense />
          <q-input v-model="password" label="Contraseña" type="password" dense class="q-mt-md" />
          <div class="q-mt-lg flex justify-end">
            <q-btn label="Entrar" color="primary" type="submit" />
          </div>
        </q-form>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../stores/authStore'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'

const email = ref('')
const password = ref('')
const auth = useAuthStore()
const router = useRouter()
const $q = useQuasar()

const onSubmit = async () => {
  try {
    await auth.login(email.value, password.value)
    router.push({ name: 'empresas' })
  } catch (e) {
    $q.notify({ type: 'negative', message: 'Credenciales inválidas' })
  }
}
</script>
