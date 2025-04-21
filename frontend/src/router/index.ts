import { createRouter, createWebHistory } from 'vue-router'
import SubestacaoListPage from '@/views/SubestacaoListPage.vue'
import SubestacaoFormPage from '@/views/SubestacaoFormPage.vue'
import SubestacaoViewPage from '../views/SubestacaoViewPage.vue'
// import RedeMTListPage from '@/views/RedeMTListPage.vue'
// import RedeMTFormPage from '@/views/RedeMTFormPage.vue'
import LoginPage from '@/views/LoginPage.vue'
import HomePage from '../views/HomePage.vue'

const routes = [
  {path: '/', name: 'Home', component: HomePage},
  { path: '/login', name: 'Login', component: LoginPage},
  { path: '/subestacoes', name: 'SubestacaoList', component: SubestacaoListPage, props: true },
  { path: '/subestacoes/novo', name: 'SubestacaoForm', component: SubestacaoFormPage },
  { path: '/subestacoes/:id', name: 'SubestacaoForm', component: SubestacaoFormPage },
  { path: '/subestacoes/view/:id', name: 'SubestacaoView', component: SubestacaoViewPage},
//   { path: '/subestacoes/:id/redesmt', name: 'RedeMTList', component: RedeMTListPage, props: true },
//   { path: '/subestacoes/:id/redesmt/novo', name: 'RedeMTForm', component: RedeMTFormPage, props: true },
//   { path: '/subestacoes/:id/redesmt/:redeId', name: 'RedeMTEdit', component: RedeMTFormPage, props: true }
]

export const router = createRouter({
  history: createWebHistory(),
  routes
})