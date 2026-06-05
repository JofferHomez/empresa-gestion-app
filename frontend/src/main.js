import { createApp } from 'vue'
import { Quasar } from 'quasar'
import { createPinia } from 'pinia'
import router from './router'
import App from './App.vue'

import './styles/quasar.sass'
import '@quasar/extras/material-icons/material-icons.css'

const app = createApp(App)

app.use(Quasar, {
  plugins: {}, // Notify, Dialog, etc si los necesitas
})

app.use(createPinia())
app.use(router)

app.mount('#app')
