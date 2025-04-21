<template>
    <form @submit.prevent="handleSubmit" class="needs-validation" novalidate>
        <div class="mb-3">
            <label for="matricula" class="form-label">Matrícula</label>
            <input
                id="matricula"
                v-model="form.matricula"
                type="text"
                class="form-control"
                placeholder="Digite a matrícula"
                required
            />
        </div>

        <div class="mb-3">
            <label for="nome" class="form-label">Nome</label>
            <input
                id="nome"
                v-model="form.nome"
                type="text"
                class="form-control"
                placeholder="Digite o nome"
                required
            />
        </div>

        <div class="mb-3">
            <label for="dataNascimento" class="form-label">Data de Nascimento</label>
            <input
                id="dataNascimento"
                v-model="form.dataNascimento"
                type="date"
                class="form-control"
                required
            />
        </div>

        <div class="mb-3">
            <label for="curso" class="form-label">Curso</label>
            <select
                id="curso"
                v-model="form.curso"
                class="form-select"
                required
            >
                <option disabled value="">Selecione o curso</option>
                <option value="IPI">IPI</option>
                <option value="ADS">ADS</option>
                <option value="ADM">ADM</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">
            {{ isEditing ? 'Atualizar' : 'Cadastrar' }}
        </button>
    </form>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { Aluno } from '@/composables/useAlunos'

const emit = defineEmits<{
    (e: 'submit', aluno: Aluno): void
}>()

const props = defineProps<{
    modelValue: Aluno | null
}>()

const form = ref<Aluno>({
    matricula: '',
    nome: '',
    dataNascimento: '',
    curso: '' as Aluno['curso']
})
const isEditing = ref(false)

watch(() => props.modelValue, (newVal) => {
    if (newVal) {
        form.value = { ...newVal,
            dataNascimento: new Date(newVal.dataNascimento).toISOString().split('T')[0]
         }
        isEditing.value = true
    } else {
        form.value = {
            matricula: '',
            nome: '',
            dataNascimento: '',
            curso: '' as Aluno['curso']
        }
        isEditing.value = false
    }
})

function handleSubmit() {
    emit('submit', form.value)
    form.value = {
        matricula: '',
        nome: '',
        dataNascimento: '',
        curso: '' as Aluno['curso']
    }
}
</script>