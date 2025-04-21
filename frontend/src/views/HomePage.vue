<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'

// Função para verificar o token
const isTokenValid = () => {
  const token = localStorage.getItem('authToken') // Substitua por sessionStorage se necessário
  if (!token) return false

  try {
    const payload = JSON.parse(atob(token.split('.')[1])) // Decodifica o payload do JWT
    console.log('Payload do token:', payload)
    const currentTime = Math.floor(Date.now() / 1000) // Tempo atual em segundos
    return payload.exp > currentTime // Verifica se o token expirou
  } catch (error) {
    console.error('Erro ao verificar o token:', error)
    return false
  }
}

const router = useRouter()

onMounted(() => {
  if (!isTokenValid()) {
    router.push('/login') // Redireciona para a página de login
  } else {
    router.push('/subestacoes') // Redireciona para a listagem de subestações
  }
})
</script>

<template>
  <div>
    <p>Verificando autenticação...</p>
  </div>
</template>