import { ref } from 'vue'
import axiosInstance from '@/config/axiosConfig' 

const API = import.meta.env.VITE_API_BASE + '/api/subestacoes'

export function useSubestacoes() {
  const subestacoes = ref([])
  const subestacao = ref({})
  const isLoading = ref(false)

  const fetchSubestacoes = async () => {
    isLoading.value = true
    const res = await axiosInstance.get(API)
    subestacoes.value = res.data
    isLoading.value = false
  }

  const getSubestacaoById = async (id: string) => {
    const res = await axiosInstance.get(`${API}/${id}`)
    return res.data
  }

  const addSubestacao = async (subestacao: any) => {
    await axiosInstance.post(API, subestacao)
    await fetchSubestacoes()
  }

  const updateSubestacao = async (subestacao: any) => {
    await axiosInstance.put(`${API}/${subestacao.id}`, subestacao)
    await fetchSubestacoes()
  }

  const deleteSubestacao = async (id: string) => {
    await axiosInstance.delete(`${API}/${id}`)
    await fetchSubestacoes()
  }

  return {
    subestacoes,
    isLoading,
    fetchSubestacoes,
    getSubestacaoById,
    addSubestacao,
    updateSubestacao,
    deleteSubestacao
  }
}