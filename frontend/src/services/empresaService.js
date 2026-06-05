import api from './api'

export default {
  async list() {
    const { data } = await api.get('/empresas')
    return data
  },
  async getByNit(nit) {
    const { data } = await api.get(`/empresas/${nit}`)
    return data
  },
  async create(payload) {
    const { data } = await api.post('/empresas', payload)
    return data
  },
  async update(nit, payload) {
    const { data } = await api.put(`/empresas/${nit}`, payload)
    return data
  },
  async remove(nit) {
    await api.delete(`/empresas/${nit}`)
  }
}
