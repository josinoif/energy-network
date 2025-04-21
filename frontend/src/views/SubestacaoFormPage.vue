<template>
  <div class="page-container">
    <HeaderTitle title="Sistema Teste Desenvolvedor Java - Versão 1.5" />

    <div class="page-container">
      <!-- Breadcrumb -->
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
            <li class="breadcrumb-item"><router-link to="/subestacoes">Subestação</router-link></li>
          <li class="breadcrumb-item active" aria-current="page">
            {{ isEditing ? 'Edição' : 'Inclusão' }}
          </li>
        </ol>
      </nav>

      <!-- Formulário de Subestação -->
      <SubestacaoForm v-model="subestacao" />

      <!-- Redes MT -->
      <div class="form-section" v-if="!isLoading">
        <h6>Rede MT</h6>
        <div class="row mb-3">
          <div class="col-md-2">
            <label for="redeCodigo" class="form-label">Código:</label>
            <input v-model="novaRede.codigo" type="text" class="form-control" id="redeCodigo" />
          </div>
          <div class="col-md-4">
            <label for="redeNome" class="form-label">Nome:</label>
            <input v-model="novaRede.nome" type="text" class="form-control" id="redeNome" />
          </div>
          <div class="col-md-2 d-flex align-items-end">
            <button class="btn btn-outline-secondary w-100" @click="adicionarRede">
              {{ isEditingRede ? 'Atualizar' : 'Adicionar' }}
            </button>
          </div>
        </div>

        <!-- Tabela de Redes MT Adicionadas -->
        <RedeMTTable :redes="subestacao.redes" @delete="removerRede" @edit="editarRede" />
      </div>

      <div v-if="isLoading" class="text-center">
        <p>Carregando dados da subestação...</p>
      </div>

      <!-- Botões Inferiores -->
      <div class="btn-group-bottom">
        <button class="btn btn-outline-secondary btn-acao" @click="cancelar">cancelar</button>
        <button class="btn btn-secondary btn-acao" @click="incluir">salvar</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axiosInstance from '@/config/axiosConfig'
import HeaderTitle from '@/components/HeaderTitle.vue'
import SubestacaoForm from '@/components/SubestacaoForm.vue'
import RedeMTTable from '@/components/RedeMTTable.vue'
import { useSubestacoes } from '@/composables/useSubestacoes'
import { useToast } from '@/composables/useToast'
import Toast from '@/components/Toast.vue'
import { useToastStore } from '@/stores/toastStore'

const { showToast, toastTitle, toastMessage, toastType, showToastMessage } = useToast()
const { getSubestacaoById } = useSubestacoes()
const route = useRoute()
const router = useRouter()
const toastStore = useToastStore()

const isLoading = ref(true)
const isEditing = ref(false)
const subestacao = ref({
  codigo: '',
  nome: '',
  latitude: '',
  longitude: '',
  redes: []
})

const novaRede = ref({ codigo: '', nome: '' }) // Objeto para nova rede
const isEditingRede = ref(false) // Indica se uma rede está sendo editada
const redeOriginal = ref(null) // Armazena a rede original que está sendo editada

// Função para buscar subestação do backend
const fetchSubestacao = async (id: string) => {
  try {
    subestacao.value = await getSubestacaoById(id)
  } catch (error) {
    console.error('Erro ao buscar subestação:', error.response?.data || error.message)
  } finally {
    isLoading.value = false
  }
}

// Função para adicionar ou atualizar uma rede
const adicionarRede = () => {
  if (novaRede.value.codigo && novaRede.value.nome) {
    if (isEditingRede.value) {
      // Atualiza a rede existente
      const index = subestacao.value.redes.findIndex((rede) => rede.codigo === redeOriginal.value.codigo)
      if (index !== -1) {
        subestacao.value.redes[index] = { ...novaRede.value }
      }
      isEditingRede.value = false
      redeOriginal.value = null
    } else {
      // Adiciona uma nova rede
      subestacao.value.redes.push({ ...novaRede.value })
    }
    novaRede.value = { codigo: '', nome: '' } // Limpa o formulário
  }
}

// Função para editar uma rede
const editarRede = (rede) => {
  novaRede.value = { ...rede }
  redeOriginal.value = { ...rede }
  isEditingRede.value = true
}

// Função para remover uma rede
const removerRede = (codigo: string) => {
  subestacao.value.redes = subestacao.value.redes.filter((rede) => rede.codigo !== codigo)
}

// Função para cancelar a operação
const cancelar = () => {
  router.push('/subestacoes')
}

// Função para salvar a subestação (inclusão ou edição)
const incluir = async () => {
  try {
    if (isEditing.value) {
      await axiosInstance.put(`/api/subestacoes/${route.params.id}`, subestacao.value)
      toastStore.setToast('Sucesso', 'Subestação atualizada com sucesso.', 'success')
    } else {
      await axiosInstance.post('/api/subestacoes', subestacao.value)
      toastStore.setToast('Sucesso', 'Subestação criada com sucesso.', 'success')
    }
    router.push('/subestacoes')
  } catch (error) {
    console.error('Erro ao salvar subestação:', error.response?.data || error.message)
    showToastMessage('Erro', 'Erro ao salvar subestação.', 'error')
  }
}

// Inicializa o componente
onMounted(async () => {
  if (route.params.id && route.params.id !== 'novo') {
    isEditing.value = true
    await fetchSubestacao(route.params.id as string)
  } else {
    isEditing.value = false
    isLoading.value = false
  }
})
</script>

<style scoped>
.page-container {
  padding: 40px;
}

.form-section {
  border: 1px solid #ced4da;
  border-radius: 5px;
  padding: 20px;
  margin-bottom: 30px;
}

.btn-group-bottom {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn-acao {
  width: 100px;
}
</style>