import { createRouter, createWebHistory } from 'vue-router';
import { nextTick } from 'vue';

const HomeView = () => import('./views/HomeView.vue');
const CallsView = () => import('./views/calls/CallsView.vue');
const CallsDashboardView = () => import('./views/calls/CallsDashboardView.vue');

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
