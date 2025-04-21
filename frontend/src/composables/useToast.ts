import { ref } from 'vue'

export function useToast() {
  const showToast = ref(false)
  const toastTitle = ref('')
  const toastMessage = ref('')
  const toastType = ref<'success' | 'error'>('success')

  const showToastMessage = (title: string, message: string, type: 'success' | 'error', duration = 3000) => {
    toastTitle.value = title
    toastMessage.value = message
    toastType.value = type
    showToast.value = true

    setTimeout(() => {
      showToast.value = false
    }, duration)
  }

  return {
    showToast,
    toastTitle,
    toastMessage,
    toastType,
    showToastMessage
  }
}