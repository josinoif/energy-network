<template>
    <table class="table table-striped table-hover">
        <thead class="thead-light">
            <tr>
                <th>Nome</th>
                <th>Matrícula</th>
                <th>Data de Nascimento</th>
                <th>Curso</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="aluno in alunos" :key="aluno.id">
                <td><strong>{{ aluno.nome }}</strong></td>
                <td>{{ aluno.matricula }}</td>
                <td>{{ aluno.dataNascimento }}</td>
                <td>{{ aluno.curso }}</td>
                <td>
                    <button class="btn btn-primary btn-sm me-2" @click="$emit('edit', aluno)">Editar</button>
                    <button class="btn btn-danger btn-sm" @click="$emit('delete', aluno.id!)">Excluir</button>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script setup lang="ts">
import type { Aluno } from '@/composables/useAlunos'

defineProps<{
    alunos: Aluno[]
}>()

defineEmits<{
    (e: 'edit', aluno: Aluno): void
    (e: 'delete', id: string): void
}>()
</script>
