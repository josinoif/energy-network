<template>
    <h1>Cadastro de Alunos</h1>
    <AlunoForm :modelValue="alunoSelecionado" @submit="onSubmit" />
    <AlunoList :alunos="alunos" @edit="onEdit" @delete="deleteAluno" />
  </template>
  
  <script setup lang="ts">
  import AlunoForm from '@/components/AlunoForm.vue'
  import AlunoList from '@/components/AlunoList.vue'
  import { useAlunos, type Aluno } from '@/composables/useAlunos'
  import { ref } from 'vue'
  
  const { alunos, addAluno, updateAluno, deleteAluno } = useAlunos()
  const alunoSelecionado = ref<Aluno | null>(null)
  
  const onSubmit = (aluno: Aluno) => {
    if (aluno.id) {
      updateAluno(aluno)
    } else {
      addAluno(aluno)
    }
    alunoSelecionado.value = null
  }
  
  const onEdit = (aluno: Aluno) => {
    alunoSelecionado.value = aluno
  }
  </script>
  