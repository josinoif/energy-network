<template>
    <div class="page-container">
      <!-- Título -->
      <HeaderTitle title="Sistema Teste Desenvolvedor Java - Versão 1.5" />
  
      <div class="page-container">
        <!-- Breadcrumb -->
      <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
            <li class="breadcrumb-item"><router-link to="/subestacoes">Subestação</router-link></li>
          <li class="breadcrumb-item active" aria-current="page">Visualização</li>
        </ol>
      </nav>
  
      <!-- Subestação -->
      <div class="form-section">
        <h6>Subestação</h6>
        <div class="row mb-3">
          <div class="col-md-2">
            <label for="codigo" class="form-label">Código:</label>
            <input type="text" class="form-control" id="codigo" :value="subestacao.codigo" readonly />
          </div>
          <div class="col-md-10">
            <label for="nome" class="form-label">Nome:</label>
            <input type="text" class="form-control" id="nome" :value="subestacao.nome" readonly />
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <label for="latitude" class="form-label">Latitude:</label>
            <input type="text" class="form-control" id="latitude" :value="subestacao.latitude" readonly />
          </div>
          <div class="col-md-6">
            <label for="longitude" class="form-label">Longitude:</label>
            <input type="text" class="form-control" id="longitude" :value="subestacao.longitude" readonly />
          </div>
        </div>
      </div>
  
      <!-- Google Maps -->
      <div class="form-section">
        <h6>Google Maps</h6>
        <iframe
          :src="googleMapsUrl"
          allowfullscreen
          loading="lazy"
        ></iframe>
      </div>
  
      <!-- Botão Voltar -->
      <div>
        <button class="btn btn-outline-secondary btn-voltar" @click="voltar">voltar</button>
      </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import HeaderTitle from '@/components/HeaderTitle.vue'
  import { onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { useSubestacoes } from '@/composables/useSubestacoes'
import { router } from '../router'

  const { getSubestacaoById } = useSubestacoes()
  const subestacao = ref({
    codigo: '',
    nome: '',
    latitude: '',
    longitude: ''
  })
  
  const googleMapsUrl = ref(
    `https://www.google.com/maps?q=${subestacao.value.latitude},${subestacao.value.longitude}&hl=pt&z=16&output=embed`
  )
  
  const route = useRoute()

  const loadData = async (id: string) => {
    try {
      const response = await getSubestacaoById(route.params.id as string)
      subestacao.value = response
      googleMapsUrl.value = `https://www.google.com/maps?q=${subestacao.value.latitude},${subestacao.value.longitude}&hl=pt&z=16&output=embed`
      console.log('googleMapsUrl:', googleMapsUrl.value)
    } catch (error) {
      console.error('Erro ao buscar os dados da subestação:', error)
    }
  }

  onMounted(() => {
    const id = route.params?.id as string
    if (id) {
      loadData(id)
    }
  })

  const voltar = () => {
    router.push('/subestacoes')
  }
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
  
  .form-section h6 {
    font-weight: 600;
    margin-bottom: 20px;
  }
  
  input[readonly] {
    background-color: #dee2e6;
    font-weight: 500;
  }
  
  .breadcrumb-item + .breadcrumb-item::before {
    content: ">";
  }
  
  iframe {
    width: 100%;
    height: 400px;
    border: none;
    border-radius: 5px;
  }
  
  .btn-voltar {
    float: right;
    width: 100px;
  }
  </style>