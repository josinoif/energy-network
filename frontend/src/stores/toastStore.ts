import { defineStore } from 'pinia'

export const useToastStore = defineStore('toast', {
  state: () => ({
    title: '',
    message: '',
    type: 'success' as 'success' | 'error'
  }),
  actions: {
    setToast(title: string, message: string, type: 'success' | 'error') {
      this.title = title
      this.message = message
      this.type = type
    },
    clearToast() {
      this.title = ''
      this.message = ''
      this.type = 'success'
    }
  }
})