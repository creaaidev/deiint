import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

const HomeView = () => import('./views/HomeView.vue');
const CallsView = () => import('./views/calls/CallsView.vue');
const CallsCreateView = () => import('./views/calls/CallsCreateView.vue');
const CallsEditView = () => import('./views/calls/CallsEditView.vue');
const CallsDashboardView = () => import('./views/calls/CallsDashboardView.vue');
const CandidatesView = () => import('./views/candidates/CandidatesView.vue');
const CandidatesCreateView = () => import('./views/candidates/CandidatesCreateView.vue');
const CandidatesEditView = () => import('./views/candidates/CandidatesEditView.vue');
const CandidatesDashboardView = () => import('./views/candidates/CandidatesDashboardView.vue');
const InterviewsView = () => import('./views/interviews/InterviewsView.vue');
const InterviewsCreateView = () => import('./views/interviews/InterviewsCreateView.vue');
const InterviewsEditView = () => import('./views/interviews/InterviewsEditView.vue');
const InterviewsDashboardView = () => import('./views/interviews/InterviewsDashboardView.vue');
const RoomsView = () => import('./views/rooms/RoomsView.vue');
const RoomsDashboardView = () => import('./views/rooms/RoomsDashboardView.vue');

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: { title: null },
    },
    {
      path: '/calls',
      name: 'calls',
      component: CallsView,
      meta: { title: 'Concursos' },
      children: [
        {
          path: 'dashboard',
          name: 'calls-dashboard',
          component: CallsDashboardView,
          meta: {
            title: 'Concursos - Dashboard',
          },
        },
        {
          path: 'create',
          name: 'calls-create',
          component: CallsCreateView,
          meta: {
            title: 'Concursos - Criar Concurso',
          },
        },
        {
          path: 'edit/:id',
          name: 'calls-edit',
          component: CallsEditView,
          meta: {
            title: 'Concursos - Editar Concurso',
          },
        },
      ],
    },
    {
      path: '/interviews',
      name: 'interviews',
      component: InterviewsView,
      meta: { title: 'Entrevistas' },
      children: [
        {
          path: 'dashboard',
          name: 'interviews-dashboard',
          component: InterviewsDashboardView,
          meta: {
            title: 'Entrevistas - Dashboard',
          },
        },
        {
          path: 'create',
          name: 'interviews-create',
          component: InterviewsCreateView,
          meta: {
            title: 'Entrevistas - Criar Entrevista',
          },
        },
        {
          path: 'edit/:id',
          name: 'interviews-edit',
          component: InterviewsEditView,
          meta: {
            title: 'Entrevistas - Editar Entrevista',
          },
        },
      ],
    },
    {
      path: '/candidates',
      name: 'candidates',
      component: CandidatesView,
      meta: { title: 'Candidatos' },
      children: [
        {
          path: 'dashboard',
          name: 'candidates-dashboard',
          component: CandidatesDashboardView,
          meta: {
            title: 'Candidatos - Dashboard',
          },
        },
        {
          path: 'create',
          name: 'candidates-create',
          component: CandidatesCreateView,
          meta: {
            title: 'Candidatos - Criar Candidato',
          },
        },
        {
          path: 'edit/:id',
          name: 'candidates-edit',
          component: CandidatesEditView,
          meta: {
            title: 'Candidatos - Editar Candidato',
          },
        },
      ],
    },
    {
      path: '/rooms',
      name: 'rooms',
      component: RoomsView,
      meta: { title: 'Salas' },
      children: [
        {
          path: 'dashboard',
          name: 'rooms-dashboard',
          component: RoomsDashboardView,
          meta: {
            title: 'Salas - Dashboard',
          },
        },
      ],
    },
  ],
});

router.afterEach((to, from) => {
  // see https://github.com/vuejs/vue-router/issues/914#issuecomment-384477609
  nextTick(
    () =>
      (document.title =
        import.meta.env.VITE_NAME +
        (to.meta.title ? ' | ' + to.meta.title : ''))
  );
});

export default router;
