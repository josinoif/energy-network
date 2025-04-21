<template>
    <table class="table table-bordered">
      <thead class="table-light">
        <tr>
          <th v-for="header in headers" :key="header">{{ header }}</th>
        </tr>
      </thead>
      <tbody>
        <slot />
      </tbody>
    </table>
  </template>
  
  <script setup lang="ts">
  defineProps<{
    headers: string[]
  }>()
  </script>