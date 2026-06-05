import api from './api'

export default {
  async list() {
    const { data } = await api.get('/inventario')
    return data
  },
  async descargarPdf() {
    const res = await api.get('/inventario/descargar-pdf', { responseType: 'blob' })
    return res.data
  },
  async enviarCorreo(payload) {
    const { data } = await api.post('/inventario/enviar-correo', payload)
    return data
  }
}
