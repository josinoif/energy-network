import { ref, onMounted } from 'vue'
import axios from 'axios'

const API = import.meta.env.VITE_API_BASE

export interface Aluno {
  id?: string
  matricula: string
  nome: string
  dataNascimento: string
  curso: 'IPI' | 'ADS' | 'ADM'
}

export function useAlunos() {
  const alunos = ref<Aluno[]>([])
  const isLoading = ref(false)

  const fetchAlunos = async () => {
    isLoading.value = true
    const res = await axios.get(API)
    alunos.value = res.data
    isLoading.value = false
  }

  const addAluno = async (aluno: Omit<Aluno, 'id'>) => {
    await axios.post(API, aluno)
    await fetchAlunos()
  }

  const deleteAluno = async (id: string) => {
    await axios.delete(`${API}/${id}`)
    await fetchAlunos()
  }

  const updateAluno = async (aluno: Aluno) => {
    const { id, ...data } = aluno
    await axios.put(`${API}/${id}`, data)
    await fetchAlunos()
  }

  const getAlunoById = async (id: string) => {
    const res = await axios.get(`${API}/${id}`)
    return res.data as Aluno
  }

  onMounted(fetchAlunos)

  return { alunos, isLoading, fetchAlunos, addAluno, deleteAluno, updateAluno, getAlunoById }
}
