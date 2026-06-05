import api from './api'

export default {
  async list() {
    const { data } = await api.get('/productos')
    return data
  },
  async get(id) {
    const { data } = await api.get(`/productos/${id}`)
    return data
  },
  async create(payload) {
    const { data } = await api.post('/productos', payload)
    return data
  },
  async update(id, payload) {
    const { data } = await api.put(`/productos/${id}`, payload)
    return data
  },
  async remove(id) {
    await api.delete(`/productos/${id}`)
  }
}
