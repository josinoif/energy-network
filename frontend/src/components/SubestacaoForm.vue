<template>
    <div class="form-section">
      <h6>Subestação</h6>
      <div class="row mb-3">
        <div class="col-md-2">
          <label for="codigo" class="form-label">Código:</label>
          <input v-model="modelValue.codigo" type="text" class="form-control" id="codigo" />
        </div>
        <div class="col-md-10">
          <label for="nome" class="form-label">Nome:</label>
          <input v-model="modelValue.nome" type="text" class="form-control" id="nome" />
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <label for="latitude" class="form-label">Latitude:</label>
          <input v-model="modelValue.latitude" type="text" class="form-control" id="latitude" />
        </div>
        <div class="col-md-6">
          <label for="longitude" class="form-label">Longitude:</label>
          <input v-model="modelValue.longitude" type="text" class="form-control" id="longitude" />
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  defineProps({
    modelValue: {
      type: Object,
      required: true
    }
  })
  
  defineEmits(['update:modelValue'])
  </script>