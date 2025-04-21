<template>
    <div>
      <h1>Lista de Alunos</h1>
      <RouterLink to="/alunos/novo">+ Novo Aluno</RouterLink>
      <AlunoList :alunos="alunos" @edit="editarAluno" @delete="deleteAluno" />
    </div>
</template>

<script setup lang="ts">
import AlunoList from '@/components/AlunoList.vue'
import { useRouter } from 'vue-router'
import { useAlunos } from '@/composables/useAlunos'

const router = useRouter()
const { alunos, deleteAluno } = useAlunos()

const editarAluno = (aluno: any) => {
  router.push(`/alunos/${aluno.id}`)
}

</script>