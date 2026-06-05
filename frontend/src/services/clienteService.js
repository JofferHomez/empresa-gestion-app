import api from './api'

export default {
  async list() {
    const { data } = await api.get('/clientes')
    return data
  },
  async get(id) {
    const { data } = await api.get(`/clientes/${id}`)
    return data
  },
  async create(payload) {
    const { data } = await api.post('/clientes', payload)
    return data
  },
  async update(id, payload) {
    const { data } = await api.put(`/clientes/${id}`, payload)
    return data
  },
  async remove(id) {
    await api.delete(`/clientes/${id}`)
  }
}
