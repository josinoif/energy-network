<template>
    <div class="bg-img">
      <HeaderTitle title="Sistema Teste Desenvolvedor Java - Versão 1.5" />
      <p v-if="errorMessage" class="alert alert-danger">{{ errorMessage }}</p>
      <LoginForm @submit="handleLogin" />
    </div>
  </template>
  
  <script setup lang="ts">
  import HeaderTitle from '@/components/HeaderTitle.vue'
  import LoginForm from '@/components/LoginForm.vue'
  import axios from 'axios'
  import { ref } from 'vue'
import { useRouter } from 'vue-router'
  
  const API_BASE = import.meta.env.VITE_API_BASE
  const errorMessage = ref<string | null>(null)
const router = useRouter()

const handleLogin = async (credentials: { usuario: string; senha: string }) => {
  try {
    const response = await axios.post(`${API_BASE}/auth/login`, {
      username: credentials.usuario,
      password: credentials.senha
    })
    console.log('Login bem-sucedido:', response.data)

    localStorage.setItem('authToken', response.data.token)

    // Redireciona para a rota /subestacoes em caso de sucesso
    router.push('/subestacoes')
  } catch (error) {
    console.error('Erro ao fazer login:', error.response?.data || error.message)

    // Exibe a mensagem de erro na tela
    errorMessage.value = 'Credenciais inválidas. Tente novamente.'
  }
}
  </script>
  
  <style scoped>
.bg-img {
  display: flex; /* Garante que o conteúdo seja centralizado */
  justify-content: center; /* Centraliza horizontalmente */
  align-items: center; /* Centraliza verticalmente */
  background-image: url('@/assets/background.png'); /* Imagem de fundo */
  background-position: center;
  height: 100vh; /* Ocupa toda a altura da tela */
}
  </style>