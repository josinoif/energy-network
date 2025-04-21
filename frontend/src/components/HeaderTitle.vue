<template>
  <div class="header-title">
    {{ title }}
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string
}>()
</script>

<style scoped>
.header-title {
  position: fixed; /* Altera de fixed para relative */
  top: 0; /* Alinha ao topo da página */
  left: 0; /* Alinha à esquerda */
  width: 100%; /* Ocupa toda a largura da página */
  background-color: #d3d3d3; /* Define o fundo cinza */
  text-align: center;
  padding: 10px;
  font-size: 1.5rem;
  font-weight: 500;
  z-index: 1000; /* Garante que o cabeçalho fique acima de outros elementos */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Adiciona uma sombra para destaque */
}
</style>