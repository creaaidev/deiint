import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

const HomeView = () => import('./views/HomeView.vue');
const CallsView = () => import('./views/calls/CallsView.vue');
const CallsDashboardView = () => import('./views/calls/CallsDashboardView.vue');
const CandidatesView = () => import('./views/candidates/CandidatesView.vue');
const CandidatesDashboardView = () => import('./views/candidates/CandidatesDashboardView.vue');
const InterviewsView = () => import('./views/interviews/InterviewsView.vue');
const InterviewsDashboardView = () => import('./views/interviews/InterviewsDashboardView.vue');

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
