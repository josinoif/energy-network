import { createRouter, createWebHistory } from 'vue-router'
import AlunoListPage from '@/views/AlunoListPage.vue'
import AlunoFormPage from '@/views/AlunoFormPage.vue'

const routes = [
  {
    path: '/',
    redirect: '/alunos'
  },
  {
    path: '/alunos',
    name: 'Alunos',
    component: AlunoListPage
  },
  {
    path: '/alunos/novo',
    name: 'NovoAluno',
    component: AlunoFormPage
  },
  {
    path: '/alunos/:id',
    name: 'EditarAluno',
    component: AlunoFormPage,
    props: true
  }
]

export const router = createRouter({
  history: createWebHistory(),
  routes
})
