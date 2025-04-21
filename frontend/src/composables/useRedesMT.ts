import { ref } from 'vue'
import axios from 'axios'

const API = import.meta.env.VITE_API_BASE + '/redesmt'

export function useRedesMT() {
  const redesMT = ref([])
  const isLoading = ref(false)

  const fetchRedesMT = async (subestacaoId: string) => {
    isLoading.value = true
    const res = await axios.get(`${API}?subestacaoId=${subestacaoId}`)
    redesMT.value = res.data
    isLoading.value = false
  }

  const getRedeMTById = async (id: string) => {
    const res = await axios.get(`${API}/${id}`)
    return res.data
  }

  const addRedeMT = async (redeMT: any) => {
    await axios.post(API, redeMT)
    await fetchRedesMT(redeMT.subestacaoId)
  }

  const updateRedeMT = async (redeMT: any) => {
    await axios.put(`${API}/${redeMT.id}`, redeMT)
    await fetchRedesMT(redeMT.subestacaoId)
  }

  const deleteRedeMT = async (id: string, subestacaoId: string) => {
    await axios.delete(`${API}/${id}`)
    await fetchRedesMT(subestacaoId)
  }

  return {
    redesMT,
    isLoading,
    fetchRedesMT,
    getRedeMTById,
    addRedeMT,
    updateRedeMT,
    deleteRedeMT
  }
}