<template>
    <div>
      <h1>{{ id ? 'Editar Aluno' : 'Cadastrar Aluno' }}</h1>
      <RouterLink to="/alunos">← Voltar</RouterLink>
      <AlunoForm :modelValue="alunoSelecionado" @submit="salvar" />
    </div>
  </template>
  
  <script setup lang="ts">
  import { onMounted, ref } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useAlunos, type Aluno } from '@/composables/useAlunos'
  import AlunoForm from '@/components/AlunoForm.vue'
  
  const route = useRoute()
  const router = useRouter()
  const id = route.params.id as string | undefined
  const { alunos, addAluno, updateAluno, getAlunoById, fetchAlunos } = useAlunos()
  const alunoSelecionado = ref<Aluno | null>(null)
  
  onMounted(async () => {
    if (id) {
      alunoSelecionado.value = await getAlunoById(id)
    }
  })
  
  const salvar = async (aluno: Aluno) => {
    if (id) {
      updateAluno(aluno)
    } else {
      addAluno(aluno)
    }
    await fetchAlunos()
    router.push('/alunos')
  }
  </script>
  