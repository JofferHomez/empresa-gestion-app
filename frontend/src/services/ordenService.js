import api from './api'

export default {
  async list() {
    const { data } = await api.get('/ordenes')
    return data
  },
  async get(id) {
    const { data } = await api.get(`/ordenes/${id}`)
    return data
  },
  async create(payload) {
    const { data } = await api.post('/ordenes', payload)
    return data
  },
  async update(id, payload) {
    const { data } = await api.put(`/ordenes/${id}`, payload)
    return data
  },
  async remove(id) {
    await api.delete(`/ordenes/${id}`)
  }
}
