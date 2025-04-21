<template>
    <div class="login-box">
      <form @submit.prevent="handleSubmit">
        <div class="mb-3">
          <label for="usuario" class="form-label">Usuário:</label>
          <input
            v-model="form.usuario"
            type="text"
            class="form-control"
            id="usuario"
            placeholder="Digite seu usuário"
            required
          />
        </div>
        <div class="mb-3">
          <label for="senha" class="form-label">Senha:</label>
          <input
            v-model="form.senha"
            type="password"
            class="form-control"
            id="senha"
            placeholder="Digite sua senha"
            required
          />
        </div>
        <button type="submit" class="btn btn-secondary w-100">Entrar</button>
      </form>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  
  const emit = defineEmits<{
    (e: 'submit', credentials: { usuario: string; senha: string }): void
  }>()
  
  const form = ref({
    usuario: '',
    senha: ''
  })
  
  const handleSubmit = () => {
    emit('submit', form.value)
  }
  </script>
  
  <style scoped>
  .login-box {
    background-color: rgba(255, 255, 255, 0.9);
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    position: absolute;
    bottom: 30px;
    right: 30px;
    width: 250px;
  }
  </style>