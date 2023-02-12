<template>
  <v-card :flat="appearance.isMobile" class="table pa-5 elevation-10">
    <h1 class="text-center">Administração de Entrevistas</h1>
    <router-view />
  </v-card>
</template>

<script setup lang="ts">
import { useAppearanceStore } from '@/stores/appearance';

const appearance = useAppearanceStore();
</script>
