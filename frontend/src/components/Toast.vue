<template>
  <div
    v-if="visible"
    class="toast-container position-fixed bottom-0 end-0 p-3"
    style="z-index: 1055"
  >
    <div
      class="toast show"
      :class="type === 'success' ? 'bg-success text-white' : 'bg-danger text-white'"
      role="alert"
      aria-live="assertive"
      aria-atomic="true"
    >
      <div class="toast-header">
        <strong class="me-auto">{{ title }}</strong>
        <button
          type="button"
          class="btn-close"
          aria-label="Close"
          @click="closeToast"
        ></button>
      </div>
      <div class="toast-body">
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'

defineProps({
  title: {
    type: String,
    default: 'Notificação'
  },
  message: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'success', // 'success' ou 'error'
    validator: (value: string) => ['success', 'error'].includes(value)
  },
  duration: {
    type: Number,
    default: 3000 // Duração do toast em milissegundos
  }
})

const visible = ref(true)

const closeToast = () => {
  visible.value = false
}

onMounted(() => {
  setTimeout(() => {
    closeToast()
  }, 3000)
})
</script>

<style scoped>
.toast-container {
  z-index: 1055;
}

.toast {
  opacity: 1;
  transition: opacity 0.5s ease-in-out;
}
</style>