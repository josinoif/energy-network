import axios from 'axios'

const API_BASE = import.meta.env.VITE_API_BASE

const axiosInstance = axios.create({
  baseURL: API_BASE
})

// Adiciona o token no cabeçalho Authorization
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken') // Recupera o token do Local Storage
  if (token) {
    config.headers.Authorization = `Bearer ${token}` // Adiciona o token no cabeçalho
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

export default axiosInstance