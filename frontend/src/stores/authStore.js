import { defineStore } from 'pinia'
import authService from '../services/authService'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: null,
    role: null
  }),
  getters: {
    isAuthenticated: state => !!state.token,
    isAdmin: state => state.role === 'ADMIN'
  },
  actions: {
    async login(email, password) {
      const res = await authService.login(email, password)
      this.token = res.token
      this.user = res.user
      this.role = res.role
      localStorage.setItem('token', this.token)
    },
    logout() {
      this.token = null
      this.user = null
      this.role = null
      localStorage.removeItem('token')
    }
  }
})
