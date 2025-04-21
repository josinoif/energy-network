<template>
    <div>
      <label class="form-label">Redes MT adicionadas</label>
      <table class="table table-bordered">
        <thead class="table-light">
          <tr>
            <th scope="col">Código</th>
            <th scope="col">Nome</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="rede in redes" :key="rede.codigo">
            <td>{{ rede.codigo }}</td>
            <td>{{ rede.nome }}</td>
            <td class="text-center">
              <a href="#" class="text-primary me-2" @click.prevent="$emit('edit', rede)">
              <i class="fas fa-edit"></i>
              </a>
              <span class="text-secondary me-2">|</span>
              <a href="#" class="text-danger" @click.prevent="$emit('delete', rede.codigo)">
              <i class="fas fa-trash-alt"></i>
              </a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script setup lang="ts">
  defineProps({
    redes: {
      type: Array,
      required: true
    }
  })
  
  defineEmits(['delete'])
  </script>