<template>
    <tr>
      <td>{{ subestacao.codigo }}</td>
      <td>{{ subestacao.nome }}</td>
      <td class="text-center">
        <button class="icon-btn" title="Excluir" @click="$emit('delete', subestacao.id)">
          <i class="fas fa-trash-alt"></i>
        </button>
      </td>
      <td class="text-center">
        <button class="icon-btn" title="Editar" @click="$emit('edit', subestacao.id)">
          <i class="fas fa-pencil-alt"></i>
        </button>
      </td>
      <td class="text-center">
        <button class="icon-btn" title="Exibir no mapa" @click="$emit('showOnMap', subestacao.id)">
          <i class="fas fa-globe"></i>
        </button>
      </td>
    </tr>
  </template>
  
  <script setup lang="ts">
  defineProps<{
    subestacao: { codigo: string; nome: string }
  }>()
  </script>
  
  <style scoped>
  .link-excluir {
    color: #0d6efd;
    text-decoration: underline;
    cursor: pointer;
  }
  
  .link-excluir:hover {
    color: #dc3545;
  }
  
  .icon-btn {
    border: none;
    background: none;
    padding: 0;
  }
  
  .icon-btn i {
    font-size: 1rem;
    color: #000;
  }
  
  .icon-btn:hover i {
    color: #0d6efd;
  }
  </style>