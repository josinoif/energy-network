<template>
  <div>
    <HeaderTitle title="Sistema Teste Desenvolvedor Java - Versão 1.5" />
    <div class="table-wrapper">
      <h6>Subestações</h6>
      <Table :headers="headers">
        <SubestacaoRow
          v-for="subestacao in subestacoes"
          :key="subestacao.codigo"
          :subestacao="subestacao"
          @delete="handleDelete"
          @edit="handleEdit"
          @showOnMap="handleShowOnMap"
        />
      </Table>
      <button class="btn btn-outline-secondary btn-incluir" @click="handleAdd">incluir</button>
    </div>

    <!-- Toast -->
    <Toast
      v-if="showToast"
      :title="toastTitle"
      :message="toastMessage"
      :type="toastType"
      :duration="3000"
      @close="showToast = false"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useSubestacoes } from '@/composables/useSubestacoes'
import HeaderTitle from '@/components/HeaderTitle.vue'
import Table from '@/components/Table.vue'
import SubestacaoRow from '@/components/SubestacaoRow.vue'
import { useToast } from '@/composables/useToast'
import Toast from '@/components/Toast.vue'
import { useRouter, useRoute } from 'vue-router'
import { useToastStore } from '@/stores/toastStore'

const toastStore = useToastStore()
const route = useRoute()
const router = useRouter()
const { subestacoes, errorMessage, fetchSubestacoes, deleteSubestacao } = useSubestacoes()
const { showToast, toastTitle, toastMessage, toastType, showToastMessage } = useToast()

const headers = ['Código', 'Nome', 'Excluir', 'Alterar', 'Exibir no mapa']

// Função para buscar subestações do backend
const fetchAll = async () => {
  try {
    await fetchSubestacoes() // Requisição GET para o backend
  } catch (error) {
    console.error('Erro ao buscar subestações:', error.response?.data || error.message)
  }
}

// Chamado ao montar o componente
onMounted(() => {
  fetchAll()

  console.log('toastStore:', toastStore)
  if (toastStore.message) {
    showToastMessage(toastStore.title, toastStore.message, toastStore.type)
    toastStore.clearToast() 
  }

})

const handleDelete = async (id: string) => {
  try {
    await deleteSubestacao(id) // Requisição DELETE para o backend
    showToastMessage('Sucesso', `Subestação excluída com sucesso.`, 'success')
    fetchSubestacoes() // Atualiza a lista após a exclusão
  } catch (error) {
    console.error('Erro ao excluir subestação:', error.response?.data || error.message)
    showToastMessage('Erro', 'Erro ao excluir subestação. Tente novamente mais tarde.', 'error')
  }
}

const handleEdit = (id: string) => {
  console.log(`Editar subestação com código: ${id}`)
  router.push(`/subestacoes/${id}`) // Redireciona para a página de inclusão
  // Aqui você pode redirecionar para a página de edição
}

const handleShowOnMap = (id: string) => {
  console.log(`Exibir no mapa subestação com código: ${id}`)
  router.push(`/subestacoes/view/${id}`) // Redireciona para a rota de visualização no mapa
}

const handleAdd = () => {
  router.push('/subestacoes/novo') // Redireciona para a página de inclusão
}
</script>

<style scoped>
.page-container {
  padding-top: 60px; /* Compensa a altura do HeaderTitle */
}

.table-wrapper {
  max-width: 800px;
  margin: 100px auto 0;
}

.btn-incluir {
  float: right;
  margin-top: 10px;
}
</style>