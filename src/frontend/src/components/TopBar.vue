<template>
  <nav>
    <v-app-bar :fixed="appearance.isMobile" style="height: 60px" class="px-5">
      <router-link :to="{ name: 'home' }">
        <v-img
          :src="istLogo"
          class="navLogo"
          width="163px"
          maxHeight="50px"
          contain
          alt="Técnico Logo"
        />
      </router-link>
      <v-spacer />

      <v-toolbar-items hide-details>
        <v-menu
          v-for="item in navbarItems"
          :key="item.name"
          offset-y
          open-on-hover
        >
          <template v-slot:activator="{ props }">
            <v-btn
              class="deiint-nav-button"
              v-bind="props"
              style="cursor: default"
              text
            >
              {{ item.name }}
            </v-btn>
          </template>

          <v-list dense>
            <v-list-item
              v-for="subItem in item.list"
              :key="subItem.name"
              @click="router.push(subItem.path)"
            >
              <template v-slot:prepend>
                <v-icon>{{ subItem.icon }}</v-icon>
              </template>
              <v-list-item-title>{{ subItem.name }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
    </v-app-bar>
  </nav>
</template>

<script setup lang="ts">
import { useAppearanceStore } from '@/stores/appearance';
import { useRouter } from 'vue-router';

import istLogo from '@/assets/img/ist_logo.svg';

const router = useRouter();
const appearance = useAppearanceStore();

const navbarItems: {
  name: string;
  list: {
    path: string;
    icon: string;
    name: string;
  }[];
}[] = [
  {
    name: 'Concursos',
    list: [
      {
        path: '/calls/dashboard',
        icon: 'fas fa-tachometer-alt',
        name: 'Dashboard',
      },
      {
        path: '/calls/create',
        icon: 'fas fa-plus',
        name: 'Criar Concurso',
      },
    ], 
  },
  {
    name: 'Entrevistas',
    list: [
      {
        path: '/interviews/dashboard',
        icon: 'fas fa-tachometer-alt',
        name: 'Dashboard',
      },
      {
        path: '/interviews/create',
        icon: 'fas fa-plus',
        name: 'Adicionar Entrevista',
      },
    ],
  },
  {
    name: 'Candidatos',
    list: [
      {
        path: '/candidates/dashboard',
        icon: 'fas fa-tachometer-alt',
        name: 'Dashboard',
      },
      {
        path: '/candidates/create',
        icon: 'fas fa-plus',
        name: 'Adicionar Candidato',
      },
    ],
  },
  {
    name: 'Salas',
    list: [
      {
        path: '/rooms/dashboard',
        icon: 'fas fa-tachometer-alt',
        name: 'Dashboard',
      },
    ],
  }
];
</script>

<style scoped>
nav {
  z-index: 10;
  margin-bottom: 60px;
}
</style>
